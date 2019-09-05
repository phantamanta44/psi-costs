package xyz.phanta.psicosts.integration.top;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

public class ProbeProviderPsiBlock implements IProbeInfoProvider {

    @Override
    public String getID() {
        return Psio.MOD_ID + ":psi_block";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player,
                             World world, IBlockState state, IProbeHitData hitData) {
        TileEntity tile = world.getTileEntity(hitData.getPos());
        if (tile != null && tile.hasCapability(PsioCaps.PSI_PROVIDER, hitData.getSideHit())) {
            PsiProvider psiBlock = Objects.requireNonNull(tile.getCapability(PsioCaps.PSI_PROVIDER, hitData.getSideHit()));
            info.progress(psiBlock.getPsiEnergy(), psiBlock.getPsiEnergyMax(), info.defaultProgressStyle()
                    .filledColor(0xFFA1AEF0).alternateFilledColor(0xFF98a4e3)
                    .backgroundColor(0xFF212121)
                    .suffix("PSI").numberFormat(NumberFormat.COMPACT));
        }
    }

}
