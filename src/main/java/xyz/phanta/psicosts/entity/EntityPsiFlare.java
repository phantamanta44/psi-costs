package xyz.phanta.psicosts.entity;

import io.github.phantamanta44.libnine.util.math.LinAlUtils;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.internal.IPlayerData;
import vazkii.psi.common.Psi;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.net.SPacketSyncPsiEnergy;

import java.util.Iterator;
import java.util.Objects;

public class EntityPsiFlare extends EntityThrowable {

    private static final float DIMS = 2.5F;
    private static final float DIMS_SQ = DIMS * DIMS;
    private static final float INITIAL_VEL = 0.25F;
    private static final float GRAVITY_FACTOR = 4F;

    private static final DataParameter<Integer> ENERGY
            = EntityDataManager.createKey(EntityPsiFlare.class, DataSerializers.VARINT);

    public EntityPsiFlare(World world) {
        super(world);
        this.ticksExisted = 3;
        setSize(DIMS, DIMS);
    }

    public EntityPsiFlare(World world, Vec3d pos, EntityPlayer target, int energy) {
        super(world, pos.x, pos.y, pos.z);
        setSize(DIMS, DIMS);
        Vec3d dPos = target.getPositionEyes(1F).subtract(pos).normalize();
        Vec3d axisA = LinAlUtils.findOrthogonal(dPos).normalize();
        Vec3d axisB = axisA.crossProduct(dPos);
        float angle = rand.nextFloat() * MathUtils.PI_F * 2F;
        dPos = dPos.add(axisA.scale(Math.cos(angle))).add(axisB.scale(Math.sin(angle))).normalize();
        this.motionX = dPos.x * INITIAL_VEL;
        this.motionY = dPos.y * INITIAL_VEL;
        this.motionZ = dPos.z * INITIAL_VEL;
        this.thrower = target;
        this.ticksExisted = 3;
        setPsiEnergy(energy);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(ENERGY, 0);
    }

    public int getPsiEnergy() {
        return dataManager.get(ENERGY);
    }

    public void setPsiEnergy(int energy) {
        dataManager.set(ENERGY, energy);
    }

    @Override
    protected float getGravityVelocity() {
        return 0F;
    }

    @Override
    public void onUpdate() {
        if (ticksExisted > 103) {
            setDead();
        } else {
            super.onUpdate();
            if (world.isRemote) {
                if (isEntityAlive()) {
                    Psi.proxy.sparkleFX(world, posX, posY, posZ, 0.3F, 0.4F, 0.9F, 4F, 3);
                }
            } else {
                EntityLivingBase target = getThrower();
                if (target == null) {
                    setDead();
                } else if (isEntityAlive()) {
                    Vec3d dPos = target.getPositionEyes(1F).subtract(posX, posY, posZ);
                    double magn = dPos.lengthSquared();
                    if (magn <= DIMS_SQ) {
                        impartTo(target);
                    } else {
                        magn = MathHelper.sqrt(magn);
                        double gravity = Math.max(magn, 1D);
                        gravity = GRAVITY_FACTOR / (magn * gravity * gravity);
                        motionX += dPos.x * gravity;
                        motionY += dPos.y * gravity;
                        motionZ += dPos.z * gravity;
                        velocityChanged = true;
                    }
                }
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote && result.entityHit != null) {
            EntityLivingBase target = getThrower();
            if (target != null && result.entityHit.getUniqueID().equals(target.getUniqueID())) {
                impartTo(target);
            }
        }
    }

    private void impartTo(EntityLivingBase target) {
        if (target instanceof EntityPlayer) {
            int energy = getPsiEnergy();
            IPlayerData playerDataUnchecked = PsiAPI.internalHandler.getDataForPlayer((EntityPlayer)target);
            if (playerDataUnchecked instanceof PlayerDataHandler.PlayerData) {
                PlayerDataHandler.PlayerData playerData = (PlayerDataHandler.PlayerData)playerDataUnchecked;
                int transferToPlayer = Math.min(playerData.getTotalPsi() - playerData.getAvailablePsi(), energy);
                if (transferToPlayer > 0) {
                    energy -= transferToPlayer;
                    playerData.availablePsi += transferToPlayer;
                    if (target instanceof EntityPlayerMP) {
                        Psio.INSTANCE.getNetworkHandler().sendTo(
                                new SPacketSyncPsiEnergy(playerData.availablePsi), (EntityPlayerMP)target);
                    }
                }
            }
            if (energy > 0) {
                Iterator<PsiCell> cellIter = Psio.PROXY.getIntegrations().getInv((EntityPlayer)target)
                        .filter(s -> s.hasCapability(PsioCaps.PSI_CELL, null))
                        .map(s -> Objects.requireNonNull(s.getCapability(PsioCaps.PSI_CELL, null)))
                        .iterator();
                while (energy > 0 && cellIter.hasNext()) {
                    energy -= cellIter.next().injectCharge(energy);
                }
            }
        }
        setDead();
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return false;
    }

}
