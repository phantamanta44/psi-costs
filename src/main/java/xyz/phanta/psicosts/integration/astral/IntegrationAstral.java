package xyz.phanta.psicosts.integration.astral;

import hellfirepvp.astralsorcery.common.block.BlockBlackMarble;
import hellfirepvp.astralsorcery.common.block.BlockMarble;
import hellfirepvp.astralsorcery.common.crafting.altar.AltarRecipeRegistry;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.AttunementRecipe;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.AttunementRecipe.AttunementAltarSlot;
import hellfirepvp.astralsorcery.common.crafting.helper.ShapedRecipe;
import hellfirepvp.astralsorcery.common.item.ItemCraftingComponent;
import io.github.phantamanta44.libnine.LibNine;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.client.render.RenderInWorldPsiCharger;
import xyz.phanta.psicosts.integration.PsioIntegration;

import static hellfirepvp.astralsorcery.common.crafting.helper.ShapedRecipeSlot.*;

@PsioIntegration.Register(IntegrationAstral.MOD_ID)
public class IntegrationAstral implements PsioIntegration {

    public static final String MOD_ID = "astralsorcery";

    private static final String RECIPE_PREF = Psio.MOD_ID + "/";

    @SuppressWarnings("NullableProblems")
    private static BlockStarlightResonator BLOCK_STARLIGHT_RESONATOR;

    @Override
    public void registerEntries() {
        if (PsioConfig.convAstralStarlight.enabled) {
            BLOCK_STARLIGHT_RESONATOR = new BlockStarlightResonator();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerEntriesClient() {
        if (PsioConfig.convAstralStarlight.enabled) {
            LibNine.PROXY.getRegistrar().queueTESRReg(
                    TileStarlightResonator.class, new RenderInWorldPsiCharger<>(0.875D, 1.25F, 0.975D, 0.05D, 1.25F));
        }
    }

    @Override
    public void registerRecipes() {
        if (PsioConfig.convAstralStarlight.enabled) {
            AttunementRecipe recipe = AltarRecipeRegistry.registerAttenuationRecipe(
                    ShapedRecipe.Builder.newShapedRecipe(RECIPE_PREF + "starlight_resonator", BLOCK_STARLIGHT_RESONATOR)
                            .addPart("ingotGold", UPPER_LEFT, UPPER_RIGHT)
                            .addPart(ItemCraftingComponent.MetaType.GLASS_LENS.asStack(), UPPER_CENTER)
                            .addPart(BlockMarble.MarbleBlockType.PILLAR.asStack(), LEFT, RIGHT)
                            .addPart(BlockBlackMarble.BlackMarbleBlockType.RAW.asStack(), CENTER, LOWER_CENTER)
                            .addPart("gemAquamarine", LOWER_LEFT, LOWER_RIGHT)
                            .unregisteredAccessibleShapedRecipe())
                    .setAttItem("dustPsi", AttunementAltarSlot.UPPER_LEFT, AttunementAltarSlot.UPPER_RIGHT,
                            AttunementAltarSlot.LOWER_LEFT, AttunementAltarSlot.LOWER_RIGHT);
            recipe.setPassiveStarlightRequirement(350);
        }
    }

}
