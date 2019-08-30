package xyz.phanta.psicosts.item.block;

import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemBlockStated;
import net.minecraft.item.ItemStack;
import xyz.phanta.psicosts.block.BlockMaterialGlassy;

public class ItemBlockMaterialGlassy extends L9ItemBlockStated implements ParameterizedItemModel.IParamaterized {

    public ItemBlockMaterialGlassy(BlockMaterialGlassy block) {
        super(block);
    }

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("type", BlockMaterialGlassy.Type.getForStack(stack).name());
    }

}
