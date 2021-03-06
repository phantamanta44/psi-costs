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

    public static final TrickDrawEnergyConfig trickDrawEnergyConfig = new TrickDrawEnergyConfig();

    public static class TrickDrawEnergyConfig {

        @Config.RequiresMcRestart
        @Config.Comment("Whether to enable to \"Trick: Draw Energy\" spell piece or not.")
        public boolean enabled = true;

        @Config.Comment({
                "The divisor for computing trick potency.",
                "potency = psiQuantity / factorPotency"
        })
        @Config.RangeDouble(min = 1D)
        public double factorPotency = 732D;

        @Config.Comment({
                "The divisor for computing trick cost.",
                "cost = psiQuantity / factorCost"
        })
        @Config.RangeDouble(min = 1D)
        public double factorCost = 52D;

    }

    // shoutouts to dudblockman for these default values
    // mostly computed roughly from the value of smelting items
    @Config.Comment({
            "Conversion scheme for Forge Energy.",
            "By default, 32 FE = 1 PSI."
    })
    public static final ConversionConfig convForgeEnergy = new ConversionConfig(1D / 32D);
    @Config.Comment({
            "Conversion scheme for Botania mana.",
            "By default, 3 mana = 5 PSI (1 tablet refills the entire PSI bar about 167 times)."
    })
    public static final ConversionConfig convBotaniaMana = new ConversionConfig(5D / 3D);
    @Config.Comment({
            "Conversion scheme for Astral Sorcery liquid starlight.",
            "By default, 1 mB starlight = 5 PSI."
    })
    public static final ConversionConfig convAstralStarlight = new ConversionConfig(5D);
    @Config.Comment({
            "Conversion scheme for Blood Magic life essence.",
            "By default, 1 LP = 10 PSI."
    })
    public static final ConversionConfig convBloodMagicLp = new ConversionConfig(10D);
    @Config.Comment({
            "Conversion scheme for Embers ember.",
            "By default, 16 ember = 25 PSI."
    })
    public static final ConversionConfig convEmbersEmber = new ConversionConfig(25D / 16D);
    @Config.Comment({
            "Conversion scheme for Thaumcraft potentia essentia.",
            "By default, 1 essentia = 200 PSI."
    })
    public static final ConversionConfig convThaumEssentia = new ConversionConfig(200D);

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
