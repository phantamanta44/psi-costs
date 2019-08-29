package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.entity.EntityPsiFlare;

public class PsioEntities {

    private static int nextId = 0;

    @InitMe
    public static void init() {
        registerEntity(LangConst.ENTITY_PSI_FLARE, EntityPsiFlare.class, 64, 2, true);
    }

    private static void registerEntity(String name, Class<? extends Entity> clazz,
                                       int trackingRange, int updateFreq, boolean velUpdates) {
        ResourceLocation id = Psio.INSTANCE.newResourceLocation(name);
        EntityRegistry.registerModEntity(id, clazz, id.toString(), nextId++, Psio.INSTANCE,
                trackingRange, updateFreq, velUpdates);
    }

    private static int getNextId() {
        return nextId++;
    }

}
