package cn.ksmcbrigade.VM;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ModManager {
    private final Logger LOGGER = LogManager.getLogger();
    private final String ModsDir;
    private final ModInfo[] mods;

    public ModManager(String modDir){
        this.ModsDir = modDir;
        if(!new File(this.ModsDir).exists()){
            new File(this.ModsDir).mkdirs();
        }
        File[] files = new File(this.ModsDir).listFiles();
        this.mods = new ModInfo[files.length];
        for(int i = 0; i < files.length; i++){
            this.mods[i] = new ModInfo(files[i]);
        }
    }

    @Override
    public String toString() {
        return "ModManager{" +
                "ModsDir='" + ModsDir + '\'' +
                ", mods=" + Arrays.toString(mods) +
                '}';
    }

    public String getModsDir() {
        return ModsDir;
    }

    public ModInfo[] getMods() {
        return mods;
    }

    public void registerMods() throws IOException {
        for(ModInfo mod : this.mods){
            try{
                mod.registerKeys();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        save();
    }

    public void save() throws IOException {
        JsonObject json = new JsonObject();
        for(ModInfo mod : this.mods){
            try{
                json.addProperty(mod.getName(),mod.isEnabled());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        Files.write(Paths.get(VapeManager.ConfigFile),json.toString().getBytes());
    }

}
