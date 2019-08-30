package xyz.phanta.psicosts.integration.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.psicosts.recipe.CellUpgradeRecipe;

import javax.annotation.Nullable;

public class CellUpgradeRecipeWrapper implements IShapedCraftingRecipeWrapper {

    private final CellUpgradeRecipe recipe;
    private final IJeiHelpers helpers;

    private CellUpgradeRecipeWrapper(CellUpgradeRecipe recipe, IJeiHelpers helpers) {
        this.recipe = recipe;
        this.helpers = helpers;
    }

    @Override
    public int getWidth() {
        return recipe.getRecipeWidth();
    }

    @Override
    public int getHeight() {
        return recipe.getRecipeHeight();
    }

    @Override
    public void getIngredients(IIngredients ings) {
        ings.setInputLists(ItemStack.class, helpers.getStackHelper().expandRecipeItemStackInputs(recipe.getIngredients()));
        ings.setOutput(ItemStack.class, recipe.getRecipeOutput());
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getRegistryName();
    }

    public static class Factory implements IRecipeWrapperFactory<CellUpgradeRecipe> {

        private final IJeiHelpers helpers;

        Factory(IJeiHelpers helpers) {
            this.helpers = helpers;
        }

        @Override
        public IRecipeWrapper getRecipeWrapper(CellUpgradeRecipe recipe) {
            return new CellUpgradeRecipeWrapper(recipe, helpers);
        }

    }

}
