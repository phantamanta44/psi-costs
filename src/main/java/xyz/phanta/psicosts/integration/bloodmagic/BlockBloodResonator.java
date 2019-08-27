package xyz.phanta.psicosts.integration.bloodmagic;

import io.github.phantamanta44.libnine.block.L9Block;
import io.github.phantamanta44.libnine.util.world.WorldBlockPos;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.util.FluidTankUtil;

public class BlockBloodResonator extends L9Block {

    public BlockBloodResonator() {
        super(LangConst.BLOCK_BLOOD_RESONATOR, Material.ROCK);
        setHardness(4F);
        setTileFactory((w, m) -> new TileBloodResonator());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileBloodResonator tile = getTileEntity(world, pos);
            if (tile != null && FluidTankUtil.fillTankFromHand(
                    tile, tile.getBloodTank().getRemainingCapacity(), player, hand)) {
                return true;
            }
            Psio.INSTANCE.getGuiHandler().openGui(
                    player, IntegrationBloodMagic.BLOOD_RESONATOR, new WorldBlockPos(world, pos));
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileBloodResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            WorldUtils.dropItem(world, pos, tile.getInputSlot().getStackInSlot());
            WorldUtils.dropItem(world, pos, tile.getBloodOrbSlot().getStackInSlot());
        }
        super.breakBlock(world, pos, state);
    }

}
