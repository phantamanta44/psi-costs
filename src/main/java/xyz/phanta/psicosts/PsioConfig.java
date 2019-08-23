package xyz.phanta.psicosts;

import net.minecraftforge.common.config.Config;

@Config(modid = Psio.MOD_ID)
public class PsioConfig {

    @Config.RequiresMcRestart
    @Config.Comment("The amount of PSI energy contained in psionic stimulators.")
    @Config.RangeInt(min = 1, max = Short.MAX_VALUE)
    public static int psiStimCapacity = 25000;

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

}
