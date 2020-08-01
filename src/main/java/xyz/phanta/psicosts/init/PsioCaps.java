package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.capability.StatelessCapabilitySerializer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.capability.PsiProvider;

public class PsioCaps {

    @CapabilityInject(PsiCell.class)
    public static Capability<PsiCell> PSI_CELL;

    @CapabilityInject(PsiProvider.class)
    public static Capability<PsiProvider> PSI_PROVIDER;

    @InitMe
    public static void init() {
        CapabilityManager.INSTANCE.register(PsiCell.class, new StatelessCapabilitySerializer<>(), PsiCell.Impl::new);
        CapabilityManager.INSTANCE.register(PsiProvider.class, new StatelessCapabilitySerializer<>(), PsiProvider.Impl::new);
    }

}
