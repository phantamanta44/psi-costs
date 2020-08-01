package xyz.phanta.psicosts;

import io.github.phantamanta44.libnine.Virtue;
import io.github.phantamanta44.libnine.util.L9CreativeTab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import xyz.phanta.psicosts.init.PsioItems;
import xyz.phanta.psicosts.item.ItemPsiCell;

@Mod(modid = Psio.MOD_ID, version = Psio.VERSION, useMetadata = true)
public class Psio extends Virtue {

    public static final String MOD_ID = "psicosts";
    public static final String VERSION = "1.0.2";

    @Mod.Instance(MOD_ID)
    public static Psio INSTANCE;

    @SidedProxy(
            clientSide = "xyz.phanta.psicosts.client.ClientProxy",
            serverSide = "xyz.phanta.psicosts.CommonProxy")
    public static CommonProxy PROXY;

    @SuppressWarnings("NotNullFieldNotInitialized")
    public static Logger LOGGER;

    public Psio() {
        super(MOD_ID, new L9CreativeTab(MOD_ID, () -> new ItemStack(PsioItems.CREATIVE_CELL)));
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        PROXY.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit(event);
    }

}
