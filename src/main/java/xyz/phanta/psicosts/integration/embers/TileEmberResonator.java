package xyz.phanta.psicosts.integration.embers;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import teamroots.embers.api.capabilities.EmbersCapabilities;
import teamroots.embers.api.tile.IExtraCapabilityInformation;
import teamroots.embers.particle.ParticleUtil;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.tile.base.TileInWorldPsiCharger;
import xyz.phanta.psicosts.util.TooltipUtils;

import java.util.List;
import java.util.Objects;

@RegisterTile(value = Psio.MOD_ID, deps = { IntegrationEmbers.MOD_ID })
public class TileEmberResonator extends TileInWorldPsiCharger implements IExtraCapabilityInformation {

    @AutoSerialize
    private final EmberPool ember = new EmberPool(getBufferSize(), this::setDirty);

    public TileEmberResonator() {
        super(PsioConfig.convEmbersEmber);
    }

    @Override
    protected CapabilityBroker initCapabilities() {
        return super.initCapabilities()
                .with(EmbersCapabilities.EMBER_CAPABILITY, ember);
    }

    public EmberPool getEmberPool() {
        return ember;
    }

    @Override
    protected void tick() {
        if (!world.isRemote && psiCharge.isNotFull()) {
            double qty = ember.getEmber();
            if (qty > 0) {
                ember.removeAmount(psiCharge.offer(Math.min(qty, getConversionRate())), true);
            }
        }
        super.tick();
        if (world.isRemote) {
            float fracPsi = getPsiProvider().getPsiEnergy() / (float)getPsiProvider().getPsiEnergyMax();
            if (fracPsi > 0) {
                ParticleUtil.spawnParticleGlow(world,
                        pos.getX() + 0.5F + (float)world.rand.nextGaussian() * 0.075F,
                        pos.getY() + 0.25F,
                        pos.getZ() + 0.5F + (float)world.rand.nextGaussian() * 0.075F,
                        (float)world.rand.nextGaussian() * 0.0085F, 0.03F, (float)world.rand.nextGaussian() * 0.0085F,
                        0.3F, 0.4F, 0.9F, 1.75F + fracPsi * 1.75F, 24);
            }
            float fracEmber = (float)(ember.getEmber() / ember.getEmberCapacity());
            if (fracEmber > 0) {
                ParticleUtil.spawnParticleGlow(world,
                        pos.getX() + 0.5F + (float)world.rand.nextGaussian() * 0.075F,
                        pos.getY() + 0.25F,
                        pos.getZ() + 0.5F + (float)world.rand.nextGaussian() * 0.075F,
                        (float)world.rand.nextGaussian() * 0.0085F, 0.03F, (float)world.rand.nextGaussian() * 0.0085F,
                        1F, 0.251F, 0.063F, 1.75F * fracEmber * 1.75F, 24);
            }
            ItemStack stack = getInputSlot().getStackInSlot();
            if (stack.hasCapability(PsioCaps.PSI_CELL, null)) {
                PsiCell cell = Objects.requireNonNull(stack.getCapability(PsioCaps.PSI_CELL, null));
                if (cell.getStoredCharge() < cell.getMaxCharge()) {
                    for (int i = 0; i < 2; i++) {
                        float bX = pos.getX() + 0.5F, bY = pos.getY() + 0.25F, bZ = pos.getZ() + 0.5F;
                        float rX = (float)world.rand.nextGaussian(), rZ = (float)world.rand.nextGaussian();
                        ParticleUtil.spawnParticleGlow(world, bX + rX * 0.1F, bY, bZ + rZ * 0.1F,
                                rX * 0.0175F, 0.025F, rZ * 0.0175F, 0.3F, 0.4F, 0.9F, 3F, 36);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasCapabilityDescription(Capability<?> capability) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public void addCapabilityDescription(List<String> strings, Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            strings.add(IntegrationEmbers.getCapInfo(EnumIOType.BOTH, IntegrationEmbers.EmbersCapType.PSI_CELL));
        }
    }

    @Override
    public void addOtherDescription(List<String> strings, EnumFacing facing) {
        strings.add(TooltipUtils.formatPsiEnergy(getPsiProvider()));
    }

}
