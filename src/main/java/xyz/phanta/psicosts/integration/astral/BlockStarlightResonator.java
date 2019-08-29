package xyz.phanta.psicosts.integration.astral;

import io.github.phantamanta44.libnine.block.L9Block;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.util.BlockInteractionUtil;
import xyz.phanta.psicosts.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;

public class BlockStarlightResonator extends L9Block {

    public BlockStarlightResonator() {
        super(LangConst.BLOCK_STARLIGHT_RESONATOR, Material.ROCK);
        setHardness(4F);
        setTileFactory((w, m) -> new TileStarlightResonator());
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TooltipUtils.formatInfo(LangConst.TT_CHARGER_ASTRAL_STARLIGHT));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileStarlightResonator tile = getTileEntity(world, pos);
        return tile != null && (BlockInteractionUtil.placeCellFromHand(tile.getInputSlot(), player, hand)
                || BlockInteractionUtil.fillTankFromHand(tile, tile.getStarlightTank().getRemainingCapacity(), player, hand));
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileStarlightResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            WorldUtils.dropItem(world, pos, tile.getInputSlot().getStackInSlot());
        }
        super.breakBlock(world, pos, state);
    }

}
