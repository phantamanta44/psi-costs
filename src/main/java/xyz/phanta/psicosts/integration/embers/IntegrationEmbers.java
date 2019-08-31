package xyz.phanta.psicosts.integration.embers;

import io.github.phantamanta44.libnine.LibNine;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.embers.api.tile.IExtraCapabilityInformation;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.client.render.RenderInWorldPsiCharger;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationEmbers.MOD_ID)
public class IntegrationEmbers implements PsioIntegration {

    public static final String MOD_ID = "embers";

    @Override
    public void registerEntries() {
        if (PsioConfig.convEmbersEmber.enabled) {
            new BlockEmberResonator();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerEntriesClient() {
        if (PsioConfig.convEmbersEmber.enabled) {
            LibNine.PROXY.getRegistrar().queueTESRReg(
                    TileEmberResonator.class, new RenderInWorldPsiCharger<>(0.75D, 1.15F, 0.9D, 0.05D, 1.25F));
        }
    }

    public static String getCapInfo(IExtraCapabilityInformation.EnumIOType io, EmbersCapType type) {
        return IExtraCapabilityInformation.formatCapability(io, type.category.key, I18n.format(type.key));
    }

    public enum EmbersCapCategory {

        ITEM("embers.tooltip.goggles.item");

        public final String key;

        EmbersCapCategory(String key) {
            this.key = key;
        }

    }

    public enum EmbersCapType {

        PSI_CELL(EmbersCapCategory.ITEM, LangConst.MISC_KEY + "embers_cap_type.psi_cell");

        public final EmbersCapCategory category;
        public final String key;

        EmbersCapType(EmbersCapCategory category, String key) {
            this.category = category;
            this.key = key;
        }

    }

}
