package xyz.phanta.psicosts.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import xyz.phanta.psicosts.recipe.CellUpgradeRecipe;

@JEIPlugin
public class IntegrationJei implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(CellUpgradeRecipe.class, new CellUpgradeRecipeWrapper.Factory(registry.getJeiHelpers()),
                VanillaRecipeCategoryUid.CRAFTING);
    }

}
