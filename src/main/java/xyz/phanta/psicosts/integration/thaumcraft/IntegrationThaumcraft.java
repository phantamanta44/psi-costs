package xyz.phanta.psicosts.integration.thaumcraft;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioGuis;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationThaumcraft.MOD_ID)
public class IntegrationThaumcraft implements PsioIntegration {

    public static final String MOD_ID = "thaumcraft";

    private static final ResourceLocation RECIPE_GROUP = new ResourceLocation("");

    public static final GuiIdentity<ContainerEssentiaResonator, GuiEssentiaResonator> ESSENTIA_RESONATOR
            = new GuiIdentity<>(LangConst.INV_ESSENTIA_RESONATOR, ContainerEssentiaResonator.class);

    @SuppressWarnings("NullableProblems")
    private static BlockEssentiaResonator BLOCK_ESSENTIA_RESONATOR;

    @Override
    public void registerEntries() {
        if (PsioConfig.convThaumEssentia.enabled) {
            BLOCK_ESSENTIA_RESONATOR = new BlockEssentiaResonator();
        }
        LibNine.PROXY.getRegistrar().queueGuiServerReg(ESSENTIA_RESONATOR,
                (p, w, x, y, z) -> new ContainerEssentiaResonator(PsioGuis.getTile(w, x, y, z), p.inventory));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerEntriesClient() {
        LibNine.PROXY.getRegistrar().queueGuiClientReg(ESSENTIA_RESONATOR,
                (c, p, w, x, y, z) -> new GuiEssentiaResonator(c));
    }

    @Override
    public void registerRecipes() {
        if (PsioConfig.convThaumEssentia.enabled) {
            ThaumcraftApi.addArcaneCraftingRecipe(BLOCK_ESSENTIA_RESONATOR.getRegistryName(),
                    new ShapedArcaneRecipe(RECIPE_GROUP, "FIRSTSTEPS", 20,
                            new AspectList().add(Aspect.FIRE, 1).add(Aspect.ORDER, 1),
                            BLOCK_ESSENTIA_RESONATOR,
                            "idi", "bcb", "idi",
                            'i', "ingotIron", 'd', "dustPsi", 'b', "plateBrass",
                            'c', new ItemStack(BlocksTC.centrifuge)));
        }
    }

}
