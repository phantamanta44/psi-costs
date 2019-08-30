package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraftforge.common.crafting.CraftingHelper;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.recipe.condition.RecipeConditionOreDict;

public class PsioRecipes {

    @InitMe
    public static void init() {
        CraftingHelper.register(Psio.INSTANCE.newResourceLocation("ore_dict"), new RecipeConditionOreDict());
    }

}
