package cn.ksmcbrigade.VM;

import cn.ksmcbrigade.VM.guis.ManagerGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod("vm")
@Mod.EventBusSubscriber
public class VapeManager {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ConfigFile = "config/vm/config.json";

    public static final ModManager Config = new ModManager("config/vm/mods/");

    public VapeManager() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("VapeManager is loading.");
        ClientRegistry.registerKeyBinding(Manager.VAPE_MANAGER_KEY);
        Config.registerMods();
        LOGGER.info("VapeManager is loaded.");
    }

    @SubscribeEvent
    public static void OnInputKeys(InputEvent.KeyInputEvent event) throws IOException {
        if(Manager.VAPE_MANAGER_KEY.isDown()){
            Minecraft.getInstance().setScreen(new ManagerGUI());
        }
        for(ModInfo modInfo: Config.getMods()){
            if(modInfo.getKey()!=null && modInfo.getKey().isDown()){
                modInfo.setEnabled(!modInfo.isEnabled());
                Config.save();
                Minecraft.getInstance().player.sendMessage(Component.nullToEmpty("§9"+I18n.get(modInfo.getName())+"§r:§6"+modInfo.isEnabled()),Minecraft.getInstance().player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) throws Exception {
        for(ModInfo modInfo: Config.getMods()){
            if(modInfo.isEnabled()){
                modInfo.Run(event.player);
            }
        }
    }
}
