package de.fightroom.punish;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Bans {
    private final File file;
    private final YamlConfiguration cfg;
    private final JavaPlugin plugin; // Store plugin instance

    public Bans(JavaPlugin plugin) {
        this.plugin = plugin; // Assign plugin instance
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        this.file = new File(dataFolder, "bans.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);

        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void ban(String uuid, String reason, long seconds) {
        if (!this.isBanned(uuid)) {
            long time = seconds == -1L ? -1L : System.currentTimeMillis() + seconds * 1000L;
            this.cfg.set("Bans." + uuid + ".time", time);
            this.cfg.set("Bans." + uuid + ".reason", reason);
            this.saveFile(); // This will now be async
        }
    }

    public void sendBanScreen(Player p) {
        p.kickPlayer(this.sendBanScreenString(p));
    }

    public String sendBanScreenString(Player p) {
        return "§cDu wurdest für §e" + this.getReason(p.getUniqueId().toString()).toUpperCase().replaceAll("_", " ") + " §cgesperrt!\n\n§cDu wirst am §7" + this.getUnbanDate(p.getUniqueId().toString()) + " §centbannt.";
    }

    public String getReason(String uuid) {
        return this.cfg.getString("Bans." + uuid + ".reason");
    }

    public String getUnbanDate(String uuid) {
        long time = this.cfg.getLong("Bans." + uuid + ".time");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return sdf.format(new Date(time));
    }

    public void unban(UUID uuid) {
        this.cfg.set("Bans." + uuid + ".time", (Object)null);
        this.cfg.set("Bans." + uuid + ".reason", (Object)null);
        this.saveFile(); // This will now be async
    }

    public long getBanMillis(String uuid) {
        return this.cfg.getLong("Bans." + uuid + ".time");
    }

    public boolean isBanned(String uuid) {
        return this.cfg.contains("Bans." + uuid + ".reason");
    }

    // Modified saveFile method to run asynchronously
    public void saveFile() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                this.cfg.save(this.file);
            } catch (IOException var2) {
                System.out.println("Couldn't save file: " + this.file.getName()); // More descriptive error
                var2.printStackTrace();
            }
        });
    }
}