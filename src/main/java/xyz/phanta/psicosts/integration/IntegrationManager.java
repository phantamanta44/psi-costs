package xyz.phanta.psicosts.integration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.util.InventoryProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class IntegrationManager {

    public IntegrationManager() {
        registerInvProvider(p -> Stream
                .of(p.inventory.mainInventory, p.inventory.offHandInventory, p.inventory.armorInventory)
                .flatMap(Collection::stream));
    }

    private final Collection<PsioIntegration> integrations = new ArrayList<>();

    public void loadIntegrations(ASMDataTable asmTable) {
        for (ASMDataTable.ASMData asmData : asmTable.getAll(PsioIntegration.Register.class.getCanonicalName())) {
            String intModId = (String)asmData.getAnnotationInfo().get("value");
            if (Loader.isModLoaded(intModId)) {
                Psio.LOGGER.info("Found integration target {}.", intModId);
                try {
                    integrations.add((PsioIntegration)Class.forName(asmData.getClassName()).newInstance());
                } catch (Exception e) {
                    Psio.LOGGER.error("Integration init failed!", e);
                }
            } else {
                Psio.LOGGER.info("Integration target {} not found; skipping integration.", intModId);
            }
        }
    }

    private final Collection<InventoryProvider> invProviders = new ArrayList<>();

    public void registerInvProvider(InventoryProvider provider) {
        invProviders.add(provider);
    }

    public Stream<ItemStack> getInv(EntityPlayer player) {
        return invProviders.stream().flatMap(p -> p.provide(player));
    }

}
