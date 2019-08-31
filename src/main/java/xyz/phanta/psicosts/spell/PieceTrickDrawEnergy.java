package xyz.phanta.psicosts.spell;

import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.internal.IPlayerData;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.entity.EntityPsiFlare;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.init.PsioSounds;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
public class PieceTrickDrawEnergy extends PieceTrick {

    private SpellParam paramPos;
    private SpellParam paramMaxDraw;

    public PieceTrickDrawEnergy(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        // have to init here because this is called in the super constructor for some reason
        addParam(paramPos = new ParamVector(SpellParam.GENERIC_NAME_POSITION, SpellParam.BLUE, false, false));
        addParam(paramMaxDraw = new ParamNumber(SpellParam.GENERIC_NAME_MAX, SpellParam.RED, false, true));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException, ArithmeticException {
        super.addToMetadata(meta);
        Double maxDraw = getParamEvaluation(paramMaxDraw);
        if (maxDraw == null || maxDraw <= 0) {
            throw new SpellCompilationException(SpellCompilationException.NON_POSITIVE_VALUE, x, y);
        }
        meta.addStat(EnumSpellStat.POTENCY, (int)Math.ceil(maxDraw / PsioConfig.trickDrawEnergyConfig.factorPotency));
        meta.addStat(EnumSpellStat.COST, (int)Math.ceil(maxDraw / PsioConfig.trickDrawEnergyConfig.factorCost));
    }

    @Nullable
    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 pos = getParamValue(context, paramPos);
        if (pos == null) {
            throw new SpellRuntimeException(SpellRuntimeException.NULL_VECTOR);
        } else if (!context.isInRadius(pos)) {
            throw new SpellRuntimeException(SpellRuntimeException.OUTSIDE_RADIUS);
        }
        int maxDraw = (int)Math.floor(this.<Double>getParamValue(context, paramMaxDraw));
        if (maxDraw <= 0) {
            throw new SpellRuntimeException(SpellRuntimeException.NEGATIVE_NUMBER);
        }
        IPlayerData playerDataUnchecked = PsiAPI.internalHandler.getDataForPlayer(context.caster);
        if (playerDataUnchecked instanceof PlayerDataHandler.PlayerData) {
            World world = context.caster.world;
            BlockPos tilePos = pos.toBlockPos();
            TileEntity tile = world.getTileEntity(tilePos);
            if (tile != null && tile.hasCapability(PsioCaps.PSI_PROVIDER, null)) {
                PlayerDataHandler.PlayerData playerData = (PlayerDataHandler.PlayerData)playerDataUnchecked;
                PsiProvider psiProvider = Objects.requireNonNull(tile.getCapability(PsioCaps.PSI_PROVIDER, null));
                int totalMissing = Psio.PROXY.getIntegrations().getInv(context.caster)
                        .filter(s -> s.hasCapability(PsioCaps.PSI_CELL, null))
                        .map(s -> Objects.requireNonNull(s.getCapability(PsioCaps.PSI_CELL, null)))
                        .mapToInt(c -> c.getMaxCharge() - c.getStoredCharge())
                        .sum() + playerData.getTotalPsi() - playerData.getAvailablePsi();
                int extracted = psiProvider.extractPsiEnergy(Math.min(totalMissing, maxDraw), true);
                if (extracted > 0) {
                    world.spawnEntity(new EntityPsiFlare(
                            world, WorldUtils.getBlockCenter(tilePos), context.caster, extracted));
                    world.playSound(
                            null, tilePos, PsioSounds.DRAW_PSI, SoundCategory.PLAYERS, 0.5F, 1F + world.rand.nextFloat());
                }
            }
        }
        return null;
    }

}
