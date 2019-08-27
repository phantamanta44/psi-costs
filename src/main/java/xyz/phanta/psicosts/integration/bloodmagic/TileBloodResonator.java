package xyz.phanta.psicosts.integration.bloodmagic;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.orb.BloodOrb;
import WayofTime.bloodmagic.orb.IBloodOrb;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import io.github.phantamanta44.libnine.capability.impl.L9AspectFluidHandler;
import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.component.reservoir.FluidReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.tile.base.TilePsiCharger;

@RegisterTile(value = Psio.MOD_ID, deps = { IntegrationBloodMagic.MOD_ID })
public class TileBloodResonator extends TilePsiCharger {

    @AutoSerialize
    private final FluidReservoir tank = new FluidReservoir(
            BlockLifeEssence.getLifeEssence(), new SimpleIntReservoir(getBufferSizeInt()));
    @AutoSerialize
    private final L9AspectSlot bloodOrbSlot = new L9AspectSlot.Observable(
            s -> s.getItem() instanceof IBloodOrb, (i, o, n) -> setDirty());

    public TileBloodResonator() {
        super(PsioConfig.convBloodMagicLp);
        tank.onQuantityChange((o, n) -> setDirty());
    }

    @Override
    protected CapabilityBroker initCapabilities() {
        return super.initCapabilities()
                .with(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, new L9AspectFluidHandler(tank));
    }

    public FluidReservoir getBloodTank() {
        return tank;
    }

    public L9AspectSlot getBloodOrbSlot() {
        return bloodOrbSlot;
    }

    @Override
    protected void tick() {
        if (!world.isRemote) {
            int missing = tank.getRemainingCapacity();
            if (missing > 0) {
                ItemStack orbStack = bloodOrbSlot.getStackInSlot();
                Item orbItem = orbStack.getItem();
                if (orbItem instanceof IBloodOrb && orbItem instanceof IBindable) {
                    BloodOrb orb = ((IBloodOrb)orbItem).getOrb(orbStack);
                    Binding binding = ((IBindable)orbItem).getBinding(orbStack);
                    if (orb != null && binding != null) {
                        SoulNetwork lpNet = NetworkHelper.getSoulNetwork(binding);
                        int currentLp = lpNet.getCurrentEssence();
                        if (currentLp > 0) {
                            tank.offer(lpNet.syphon(SoulTicket.block(world, pos, Math.min(missing, currentLp))), true);
                        }
                    }
                }
            }
        }
        genPsiFromIntReservoir(tank);
        super.tick();
    }

}
