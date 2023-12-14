package cn.ksmcbrigade.VM;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ClientRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModInfo {

    private final Logger LOGGER = LogManager.getLogger();

    private final String name;
    private final String id;
    private final String main;
    private final String function;
    private final String function_2;

    private boolean enabled;

    private final String gui_main;
    private final String gui_function;

    private final KeyMapping key;
    private final Screen screen;

    public ModInfo(File file){
        try{
            JsonObject json = (JsonObject) JsonParser.parseString(Files.readString(file.toPath()));
            this.name = json.get("name").getAsString();
            this.id = json.get("id").getAsString();
            this.main = json.get("main").getAsString();
            this.function = json.get("function").getAsString();
            this.function_2 = json.get("function_2").getAsString();
            this.gui_main = json.get("gui_main").getAsString();
            this.gui_function = json.get("gui_function").getAsString();

            this.enabled = isEnabled_register();

            this.key = getKey_register();
            this.screen = getScreen_register();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean isEnabled_register() throws Exception{
        if(!new File(VapeManager.ConfigFile).exists()){
            return false;
        }
        else {
            final Path path = Paths.get(VapeManager.ConfigFile);
            if(Files.readString(path).equalsIgnoreCase("{}") | Files.readString(path).equalsIgnoreCase("")){
                return false;
            }
            else if(((JsonObject) JsonParser.parseString(Files.readString(path))).get(this.name) == null){
                return false;
            }
            else{
                JsonObject json = (JsonObject) JsonParser.parseString(Files.readString(path));
                return json.get(this.name).getAsBoolean();
            }
        }
    }

    @Override
    public String toString() {
        return "ModInfo{" +
                "LOGGER=" + LOGGER +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", main='" + main + '\'' +
                ", function='" + function + '\'' +
                ", function_2='" + function_2 + '\'' +
                ", enabled=" + enabled +
                ", gui_main='" + gui_main + '\'' +
                ", gui_function='" + gui_function + '\'' +
                ", key=" + key +
                ", screen=" + screen +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getFunction() {
        return function;
    }

    public String getFunction_2() {
        return function_2;
    }

    public String getGui_main() {
        return gui_main;
    }

    public String getGui_function() {
        return gui_function;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public KeyMapping getKey() {
        return this.key;
    }

    public Screen getScreen() {
        return this.screen;
    }

    public KeyMapping getKey_register() throws Exception {
        if(!this.function.equalsIgnoreCase("none")){
            Class<?> clazz = Class.forName(this.main);
            Method method = clazz.getDeclaredMethod(this.function);
            method.setAccessible(true);
            return (KeyMapping) method.invoke(clazz.getDeclaredConstructor().newInstance());
        }
        return null;
    }

    public Screen getScreen_register() throws Exception {
        if(!this.gui_main.equalsIgnoreCase("none")){
            Class<?> clazz = Class.forName(this.gui_main);
            Method method = clazz.getDeclaredMethod(this.gui_function);
            method.setAccessible(true);
            return (Screen) method.invoke(clazz.getDeclaredConstructor().newInstance());
        }
        return null;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public void invokeFunctionInMain(String function,String... args) throws Exception {
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = String.class;
        }
        Class<?> clazz = Class.forName(this.main);
        Method method = clazz.getDeclaredMethod(function,parameterTypes);
        method.setAccessible(true);
        method.invoke(clazz.getDeclaredConstructor().newInstance(), (Object[]) args);
    }

    public void invokeFunctionInGuiMain(String function,String... args) throws Exception {
        if(!this.gui_main.equalsIgnoreCase("none")){
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = String.class;
            }
            Class<?> clazz = Class.forName(this.gui_main);
            Method method = clazz.getDeclaredMethod(function,parameterTypes);
            method.setAccessible(true);
            method.invoke(clazz.getDeclaredConstructor().newInstance(), (Object[]) args);
        }
    }

    public void registerKeys() throws Exception {
        if(!this.function.equalsIgnoreCase("none")){
            Class<?> clazz = Class.forName(this.main);
            Method method = clazz.getDeclaredMethod(this.function);
            method.setAccessible(true);
            ClientRegistry.registerKeyBinding((KeyMapping) method.invoke(clazz.getDeclaredConstructor().newInstance()));
        }
    }

    public void OpenScreen(){
        if(!this.gui_main.equalsIgnoreCase("none")){
            Minecraft.getInstance().setScreen(getScreen());
        }
    }

    public void Run(Player player) throws Exception{
        Class<?>[] parameterTypes = new Class[]{Player.class};
        Class<?> clazz = Class.forName(this.main);
        Method method = clazz.getDeclaredMethod(this.function_2,parameterTypes);
        method.setAccessible(true);
        method.invoke(clazz.getDeclaredConstructor().newInstance(),player);
    }
}
