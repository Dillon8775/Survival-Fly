package net.dillon.survivalfly.option;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil.GSON;

public class ModOptions {
    public static final String CONFIG = "survivalfly-config.json";
    private static File file;
    public static ModOptions OPTIONS = getConfig();

    public PermissionLevel permissionLevel = PermissionLevel.REGULAR;
    public ChangeFlySpeedOnRule changeFlySpeedOnRule = ChangeFlySpeedOnRule.NON_SURVIVAL_LIKE_GAMEMODES;
    public boolean showConfigButton = true;

    /**
     * Loads the configuration file.
     */
    public static void loadConfig() {
        File configFile = getConfigFile();

        if (!configFile.exists()) {
            OPTIONS = new ModOptions();
        } else {
            readConfig();
        }
        saveConfig();
    }

    /**
     * Reads the configuration file.
     */
    public static void readConfig() {
        OPTIONS = getConfig();
    }

    /**
     * Saves the configuration file.
     */
    public static void saveConfig() {
        File file = getConfigFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(GSON.toJson(OPTIONS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the {@code OPTIONS} variable to the config.
     */
    public static void setConfig(ModOptions config) {
        OPTIONS = config;
        saveConfig();
    }

    /**
     * Gets all the Speedrunner Mod configuration options and returns them.
     */
    public static ModOptions getConfig() {
        File file = getConfigFile();
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, ModOptions.class);
        } catch (Exception e) {
            ModOptions newconfig = new ModOptions();
            setConfig(newconfig);
            return newconfig;
        }
    }

    /**
     * Returns the Speedrunner Mod configuration file.
     */
    public static File getConfigFile() {
        if (file == null) {
            file = new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG);
        }
        return file;
    }
}