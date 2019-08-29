package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.util.SoundEvent;
import xyz.phanta.psicosts.Psio;

@SuppressWarnings("NullableProblems")
public class PsioSounds {

    public static SoundEvent DRAW_PSI;

    @InitMe(Psio.MOD_ID)
    public static void init() {
        DRAW_PSI = Psio.INSTANCE.newSoundEvent("psio.effect.draw_psi");
    }

}
