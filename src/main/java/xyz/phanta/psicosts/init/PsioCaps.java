package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.capability.StatelessCapabilitySerializer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import xyz.phanta.psicosts.capability.PsiCell;

public class PsioCaps {

    @CapabilityInject(PsiCell.class)
    public static Capability<PsiCell> PSI_CELL;

    @InitMe
    public static void init() {
        CapabilityManager.INSTANCE.register(PsiCell.class, new StatelessCapabilitySerializer<>(), PsiCell.Impl::new);
    }

}
