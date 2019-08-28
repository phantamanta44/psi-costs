package xyz.phanta.psicosts.block;

import io.github.phantamanta44.libnine.block.L9Block;
import io.github.phantamanta44.libnine.util.world.WorldBlockPos;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioGuis;
import xyz.phanta.psicosts.tile.TileFluxResonator;
import xyz.phanta.psicosts.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFluxResonator extends L9Block {

    public BlockFluxResonator() {
        super(LangConst.BLOCK_FLUX_RESONATOR, Material.IRON);
        setHardness(4F);
        setTileFactory((w, m) -> new TileFluxResonator());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TooltipUtils.formatInfo(LangConst.TT_CHARGER_FORGE_ENERGY));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            Psio.INSTANCE.getGuiHandler().openGui(player, PsioGuis.FLUX_RESONATOR, new WorldBlockPos(world, pos));
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileFluxResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            WorldUtils.dropItem(world, pos, tile.getInputSlot().getStackInSlot());
        }
        super.breakBlock(world, pos, state);
    }

}
