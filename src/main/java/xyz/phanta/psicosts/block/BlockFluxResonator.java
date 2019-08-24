package xyz.phanta.psicosts.block;

import io.github.phantamanta44.libnine.block.L9Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.tile.TileFluxResonator;

public class BlockFluxResonator extends L9Block {

    public BlockFluxResonator() {
        super(LangConst.BLOCK_FLUX_RESONATOR, Material.IRON);
        setTileFactory((w, m) -> new TileFluxResonator());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            // TODO open gui
        }
        return true;
    }

}
