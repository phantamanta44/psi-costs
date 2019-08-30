package xyz.phanta.psicosts.block;

import io.github.phantamanta44.libnine.block.L9BlockStated;
import io.github.phantamanta44.libnine.item.L9ItemBlock;
import io.github.phantamanta44.libnine.util.collection.Accrue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioBlocks;
import xyz.phanta.psicosts.item.block.ItemBlockMaterialGlassy;

public class BlockMaterialGlassy extends L9BlockStated {

    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockMaterialGlassy() {
        super(LangConst.BLOCK_MATERIAL_GLASSY, Material.GLASS);
        setHardness(0.3F);
        setSoundType(SoundType.GLASS);
    }

    @Override
    protected L9ItemBlock initItemBlock() {
        return new ItemBlockMaterialGlassy(this);
    }

    @Override
    protected void accrueProperties(Accrue<IProperty<?>> props) {
        props.accept(TYPE);
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

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public enum Type implements IStringSerializable {

        PSI_GLASS;

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            return VALUES[meta];
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public ItemStack newStack(int count) {
            return new ItemStack(PsioBlocks.MATERIAL_GLASSY, count, ordinal());
        }

    }

}
