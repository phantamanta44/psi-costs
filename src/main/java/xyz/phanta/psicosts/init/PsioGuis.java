package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.Registrar;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import io.github.phantamanta44.libnine.tile.L9TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.client.gui.GuiFluxResonator;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.inventory.ContainerFluxResonator;

import java.util.Objects;

public class PsioGuis {

    public static final GuiIdentity<ContainerFluxResonator, GuiFluxResonator> FLUX_RESONATOR
            = new GuiIdentity<>(LangConst.INV_FLUX_RESONATOR, ContainerFluxResonator.class);

    @InitMe(Psio.MOD_ID)
    public static void init() {
        Registrar reg = LibNine.PROXY.getRegistrar();
        reg.queueGuiServerReg(FLUX_RESONATOR, (p, w, x, y, z) -> new ContainerFluxResonator(getTile(w, x, y, z), p.inventory));
    }

    @SideOnly(Side.CLIENT)
    @InitMe(value = Psio.MOD_ID, sides = { Side.CLIENT })
    public static void initClient() {
        Registrar reg = LibNine.PROXY.getRegistrar();
        reg.queueGuiClientReg(FLUX_RESONATOR, (c, p, w, x, y, z) -> new GuiFluxResonator(c));
    }

    @SuppressWarnings("unchecked")
    private static <T extends L9TileEntity> T getTile(World world, int x, int y, int z) {
        return (T)Objects.requireNonNull(world.getTileEntity(new BlockPos(x, y, z)));
    }

}
