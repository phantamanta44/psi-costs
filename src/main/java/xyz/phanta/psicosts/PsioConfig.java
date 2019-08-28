package xyz.phanta.psicosts;

import net.minecraftforge.common.config.Config;

@Config(modid = Psio.MOD_ID, name = "psio")
public class PsioConfig {

    @Config.RequiresMcRestart
    @Config.Comment("The amount of PSI energy contained in psionic stimulators.")
    @Config.RangeInt(min = 1, max = Short.MAX_VALUE)
    public static int psiStimCapacity = 25000;

    @Config.Comment("Whether to fully level up all players on joining the server or not.")
    public static boolean maximumPsiOnJoin = true;

    public static final PsiCellConfig psiCellConfig = new PsiCellConfig();

    public static class PsiCellConfig {

        @Config.RequiresMcRestart
        @Config.Comment("The capacity of the tier 1 psionic cell.")
        @Config.RangeInt(min = 1)
        public int tier1Capacity = 80000;

        @Config.RequiresMcRestart
        @Config.Comment("The capacity of the tier 2 psionic cell.")
        @Config.RangeInt(min = 1)
        public int tier2Capacity = 240000;

        @Config.RequiresMcRestart
        @Config.Comment("The capacity of the tier 3 psionic cell.")
        @Config.RangeInt(min = 1)
        public int tier3Capacity = 720000;

        @Config.RequiresMcRestart
        @Config.Comment("The capacity of the tier 4 psionic cell.")
        @Config.RangeInt(min = 1)
        public int tier4Capacity = 2560000;

    }

    public static final XpConfig xpConfig = new XpConfig();

    public static class XpConfig {

        @Config.Comment({
                "The fraction of XP gain that is converted to PSI energy. Set to 0 to disable.",
                "If a player's PSI energy is full, all XP gain is kept as XP regardless of this setting.",
                "Be careful with disabling this setting: it could lock new players out of Psi entirely!"
        })
        @Config.RangeDouble(min = 0, max = 1)
        public double xpPsiFraction = 0.75D;

        @Config.Comment({
                "The conversion factor from XP points to PSI energy.",
                "The larger the number, the more PSI energy a player will receive per XP point."
        })
        @Config.RangeDouble(min = 1)
        public double xpPsiMultiplier = 150D;

    }

    @Config.Comment({
            "Conversion scheme for Forge Energy.",
            "By default, 5 FE = 1 PSI."
    })
    public static final ConversionConfig convForgeEnergy = new ConversionConfig(0.2D);
    @Config.Comment({
            "Conversion scheme for Botania mana.",
            "By default, 2 mana = 1 PSI (1 tablet refills the entire PSI bar about 50 times)."
    })
    public static final ConversionConfig convBotaniaMana = new ConversionConfig(0.5D);
    @Config.Comment({
            "Conversion scheme for Astral Sorcery liquid starlight.",
            "By default, 1 mB starlight = 1 PSI."
    })
    public static final ConversionConfig convAstralStarlight = new ConversionConfig(1D);
    @Config.Comment({
            "Conversion scheme for Blood Magic life essence.",
            "By default, 1 LP = 3 PSI."
    })
    public static final ConversionConfig convBloodMagicLp = new ConversionConfig(3D);
    @Config.Comment({
            "Conversion scheme for Embers ember.",
            "By default, 1 ember = 5 PSI."
    })
    public static final ConversionConfig convEmbersEmber = new ConversionConfig(5D);
    @Config.Comment({
            "Conversion scheme for Thaumcraft potentia essentia.",
            "By default, 1 essentia = 250 PSI."
    })
    public static final ConversionConfig convThaumEssentia = new ConversionConfig(250D);

    public static class ConversionConfig {

        private ConversionConfig(double defaultRatio) {
            this.ratio = defaultRatio;
        }

        @Config.RequiresMcRestart
        @Config.Comment("Whether this conversion scheme is enabled or not.")
        public boolean enabled = true;

        @Config.Comment("The conversion ratio from this scheme to PSI energy.")
        @Config.RangeDouble(min = 0)
        public double ratio;

        @Config.Comment("The size of the conversion device's PSI energy buffer.")
        @Config.RangeInt(min = 1)
        public int psiBuffer = 80000;

        @Config.Comment("The maximum rate (in PSI/tick) at which PSI energy can be produced.")
        @Config.RangeInt(min = 1)
        public int maxConversionRate = 5000;

    }

}
