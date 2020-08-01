package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;

public class PsioSounds {

    @GameRegistry.ObjectHolder(Psio.MOD_ID + ":" + LangConst.SOUND_DRAW_PSI)
    public static SoundEvent DRAW_PSI;

    @InitMe(Psio.MOD_ID)
    public static void init() {
        DRAW_PSI = Psio.INSTANCE.newSoundEvent(LangConst.SOUND_DRAW_PSI);
    }

}
