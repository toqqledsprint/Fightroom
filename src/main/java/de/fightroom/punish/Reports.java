package de.fightroom.punish;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin; // Import JavaPlugin

public class Reports {
    private static File file;
    private static FileConfiguration customFile;

    public static void setup() {
        // NOTE: Deleting the file on every setup means reports are lost on reload/restart.
        // If you want persistence, remove the file.delete() call.
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Fightroom").getDataFolder(), "reports.yml");
        if (file.exists()) {
            // file.delete(); // Consider removing this line if you want reports to persist
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var1) {
                var1.printStackTrace(); // Added printStackTrace
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    // Modified save method to take the plugin instance and run asynchronously
    public static void save(JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                customFile.save(file);
            } catch (IOException var1) {
                System.out.println("Couldn't save file: " + file.getName()); // More descriptive error
                var1.printStackTrace();
            }
        });
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}