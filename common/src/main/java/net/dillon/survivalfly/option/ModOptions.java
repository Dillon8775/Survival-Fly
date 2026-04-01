package net.dillon.survivalfly.option;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dillon.survivalfly.platform.MultiLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ModOptions {
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    public static final String CONFIG = "survivalfly-config.json";
    private static File file;
    public static ModOptions OPTIONS = getConfig();

    public Permissions permissions = Permissions.ANYONE;
    public ConfigButton configButton = ConfigButton.EVERYWHERE;
    public boolean elytraFlight = false;
    public boolean flightExhaustion = false;
    public boolean friendlyFlight = false;

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
     * Gets all the Survival Fly configuration options and returns them.
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
     * Returns the Survival Fly configuration file.
     */
    public static File getConfigFile() {
        if (file == null) {
            file = MultiLoader.PLATFORM.getConfigDir(CONFIG);
        }
        return file;
    }
}