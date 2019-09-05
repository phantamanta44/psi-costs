package xyz.phanta.psicosts.integration;

import io.github.phantamanta44.libnine.LibNine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
        for (ASMDataTable.ASMData asmData : asmTable.getAll(PsioIntegration.Register.class.getName())) {
            String intModId = (String)asmData.getAnnotationInfo().get("value");
            if (Loader.isModLoaded(intModId)) {
                Psio.LOGGER.info("Found integration target {}.", intModId);
                try {
                    integrations.add((PsioIntegration)Class.forName(asmData.getClassName()).newInstance());
                } catch (Exception e) {
                    Psio.LOGGER.error("Integration loading failed!", e);
                }
            } else {
                Psio.LOGGER.info("Integration target {} not found; skipping integration.", intModId);
            }
        }
        LibNine.PROXY.getRegistrar().begin(Psio.INSTANCE);
        for (PsioIntegration integration : integrations) {
            try {
                integration.registerEntries();
            } catch (Exception e) {
                Psio.LOGGER.error("Integration registration failed for {}!", integration.getClass());
            }
        }
        LibNine.PROXY.getRegistrar().end();
    }

    @SideOnly(Side.CLIENT)
    public void initClient() {
        LibNine.PROXY.getRegistrar().begin(Psio.INSTANCE);
        for (PsioIntegration integration : integrations) {
            try {
                integration.registerEntriesClient();
            } catch (Exception e) {
                Psio.LOGGER.error("Integration client registration failed for {}!", integration.getClass());
            }
        }
        LibNine.PROXY.getRegistrar().end();
    }

    public void initRecipes() {
        for (PsioIntegration integration : integrations) {
            try {
                integration.registerRecipes();
            } catch (Exception e) {
                Psio.LOGGER.error("Integration recipe registration failed for {}!", integration.getClass());
            }
        }
    }

    public void dispatchLateRegistration() {
        for (PsioIntegration integration : integrations) {
            try {
                integration.lateRegister();
            } catch (Exception e) {
                Psio.LOGGER.error("Integration late registration failed for {}!", integration.getClass());
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
