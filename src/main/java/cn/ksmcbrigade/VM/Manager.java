package cn.ksmcbrigade.VM;

import cn.ksmcbrigade.VM.guis.ExampleGUI;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class Manager {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String KEY_CATEGORY_VM = "key.category.vm.keys";
    public static final String KEY_VAPE_MANAGER =  "key.vm.vape_manager";

    public static final KeyMapping VAPE_MANAGER_KEY = new KeyMapping(KEY_VAPE_MANAGER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V,KEY_CATEGORY_VM);

    public static final KeyMapping EXAMPLE_KEY = new KeyMapping("key.vm.example", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F12,KEY_CATEGORY_VM);

    public static KeyMapping getKey(){
        return EXAMPLE_KEY;
    }

    public static Screen getScreen(){
        return new ExampleGUI();
    }

    public static void runKey(Player player){
        player.sendMessage(Component.nullToEmpty("Vape manager v1.0"),player.getUUID());
    }
}
