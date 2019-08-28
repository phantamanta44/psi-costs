package xyz.phanta.psicosts.integration.thaumcraft;

import io.github.phantamanta44.libnine.block.L9Block;
import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.util.world.WorldBlockPos;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.consumables.ItemPhial;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;

public class BlockEssentiaResonator extends L9Block {

    public BlockEssentiaResonator() {
        super(LangConst.BLOCK_ESSENTIA_RESONATOR, Material.IRON);
        setHardness(4F);
        setTileFactory((w, m) -> new TileEssentiaResonator());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEssentiaResonator tile = getTileEntity(world, pos);
            if (tile != null) {
                IIntReservoir essentia = tile.getEssentiaTank();
                int missing = essentia.getRemainingCapacity();
                if (missing > 0) {
                    ItemStack held = player.getHeldItem(hand);
                    if (held.getItem() instanceof ItemPhial) {
                        AspectList aspects = ((ItemPhial)held.getItem()).getAspects(held);
                        int available = aspects.getAmount(Aspect.ENERGY);
                        if (available > 0 && available <= missing) {
                            essentia.offer(available, true);
                            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 0.25F, 1F);
                            if (!player.capabilities.isCreativeMode) {
                                held.shrink(1);
                                if (held.isEmpty()) {
                                    player.setHeldItem(hand, new ItemStack(ItemsTC.phial, 1, 0));
                                } else {
                                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemsTC.phial, 1, 0));
                                }
                            }
                            return true;
                        }
                    }
                }
            }
            Psio.INSTANCE.getGuiHandler()
                    .openGui(player, IntegrationThaumcraft.ESSENTIA_RESONATOR, new WorldBlockPos(world, pos));
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEssentiaResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            WorldUtils.dropItem(world, pos, tile.getInputSlot().getStackInSlot());
        }
        super.breakBlock(world, pos, state);
    }

}
