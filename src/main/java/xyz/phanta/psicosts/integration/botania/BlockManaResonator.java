package xyz.phanta.psicosts.integration.botania;

import io.github.phantamanta44.libnine.block.L9Block;
import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.client.core.handler.HUDHandler;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.util.TooltipUtils;

public class BlockManaResonator extends L9Block implements IWandHUD {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0D, 0D, 0D, 1D, 0.8125D, 1D);

    public BlockManaResonator() {
        super(LangConst.BLOCK_MANA_RESONATOR, Material.ROCK);
        setHardness(4F);
        setTileFactory((w, m) -> new TileManaResonator());
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
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
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileManaResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            WorldUtils.dropItem(world, pos, tile.getInputSlot().getStackInSlot());
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
        TileManaResonator tile = getTileEntity(world, pos);
        if (tile != null) {
            int centerX = res.getScaledWidth() / 2, centerY = res.getScaledHeight() / 2;
            IIntReservoir mana = tile.getManaReservoir();
            HUDHandler.drawSimpleManaHUD(0x00FF00, mana.getQuantity(), mana.getCapacity(), getLocalizedName(), res);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            PsiProvider psi = tile.getPsiProvider();
            HUDHandler.renderManaBar(centerX - 51, centerY + 28, 0xA1AEF0, 1F, psi.getPsiEnergy(), psi.getPsiEnergyMax());
            String psiStr = TooltipUtils.formatPsiEnergy(psi);
            mc.fontRenderer.drawStringWithShadow(
                    psiStr, centerX - mc.fontRenderer.getStringWidth(psiStr) / 2F, centerY + 36, 0xA1AEF0);
            GlStateManager.disableBlend();
        }
    }

}
