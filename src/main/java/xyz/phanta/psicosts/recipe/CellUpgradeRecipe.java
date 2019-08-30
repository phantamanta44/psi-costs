package xyz.phanta.psicosts.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

// adapted from algane's LaserUpgradeRecipe
// https://github.com/phantamanta44/algane/blob/1.12/src/main/java/xyz/phanta/algane/recipe/LaserUpgradeRecipe.java
public class CellUpgradeRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IShapedRecipe {

    private final ShapedOreRecipe backing;

    private CellUpgradeRecipe(ShapedOreRecipe backing) {
        this.backing = backing;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return backing.matches(inv, world);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = backing.getCraftingResult(inv);
        if (result.hasCapability(PsioCaps.PSI_CELL, null)) {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack ing = inv.getStackInSlot(i);
                if (ing.hasCapability(PsioCaps.PSI_CELL, null)) {
                    Objects.requireNonNull(result.getCapability(PsioCaps.PSI_CELL, null)).setCharge(
                            Objects.requireNonNull(ing.getCapability(PsioCaps.PSI_CELL, null)).getStoredCharge());
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return backing.canFit(width, height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return backing.getRecipeOutput();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return backing.getIngredients();
    }

    @Override
    public String getGroup() {
        return backing.getGroup();
    }

    @Override
    public int getRecipeWidth() {
        return backing.getRecipeWidth();
    }

    @Override
    public int getRecipeHeight() {
        return backing.getRecipeHeight();
    }

    public static class Factory implements IRecipeFactory {

        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            return new CellUpgradeRecipe(ShapedOreRecipe.factory(context, json));
        }

    }

}
