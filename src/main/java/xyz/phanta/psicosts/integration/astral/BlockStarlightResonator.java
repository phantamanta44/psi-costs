package xyz.phanta.psicosts.integration.astral;

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
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;

import java.util.Objects;

public class BlockStarlightResonator extends L9Block {

    public BlockStarlightResonator() {
        super(LangConst.BLOCK_STARLIGHT_RESONATOR, Material.ROCK);
        setHardness(4F);
        setTileFactory((w, m) -> new TileStarlightResonator());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileStarlightResonator tile = getTileEntity(world, pos);
            if (tile != null) {
                int missing = tile.getStarlightTank().getRemainingCapacity();
                if (missing > 0) {
                    FluidActionResult result = FluidUtil.tryEmptyContainer(player.getHeldItem(hand),
                            Objects.requireNonNull(tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)),
                            missing, player, true);
                    if (result.success) {
                        if (!player.capabilities.isCreativeMode) {
                            player.setHeldItem(hand, result.result);
                        }
                        return true;
                    }
                }
            }
            Psio.INSTANCE.getGuiHandler().openGui(
                    player, IntegrationAstral.STARLIGHT_RESONATOR, new WorldBlockPos(world, pos));
        }
        return true;
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