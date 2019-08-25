package xyz.phanta.psicosts.integration.botania;

import io.github.phantamanta44.libnine.block.L9Block;
import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.client.core.handler.HUDHandler;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioCaps;

public class BlockManaResonator extends L9Block implements IWandHUD {

    public BlockManaResonator() {
        super(LangConst.BLOCK_MANA_RESONATOR, Material.ROCK);
        setHardness(4F);
        setTileFactory((w, m) -> new TileManaResonator());
        // TODO special render
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileManaResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            L9AspectSlot inputSlot = tile.getInputSlot();
            ItemStack inSlot = inputSlot.getStackInSlot();
            ItemStack held = player.getHeldItem(hand);
            if (inSlot.isEmpty()) {
                if (held.hasCapability(PsioCaps.PSI_CELL, null)) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                    inputSlot.setStackInSlot(held);
                    return true;
                }
            } else {
                inputSlot.setStackInSlot(ItemStack.EMPTY);
                if (held.isEmpty()) {
                    player.setHeldItem(hand, inSlot);
                } else {
                    ItemHandlerHelper.giveItemToPlayer(player, inSlot);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
        TileManaResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            IIntReservoir mana = tile.getManaReservoir();
            HUDHandler.drawSimpleManaHUD(0xA1AEF0, mana.getQuantity(), mana.getCapacity(), getLocalizedName(), res);
            // TODO render held item
            // TODO render psi buffer
        }
    }

}
