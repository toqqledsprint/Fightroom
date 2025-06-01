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

public class Mutes {
    private final File file;
    private final YamlConfiguration cfg;
    private final JavaPlugin plugin; // Store plugin instance

    public Mutes(JavaPlugin plugin) {
        this.plugin = plugin; // Assign plugin instance
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        this.file = new File(dataFolder, "mutes.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);

        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void mute(String uuid, String reason, long seconds) {
        if (!this.isMuted(uuid)) {
            long time = seconds == -1L ? -1L : System.currentTimeMillis() + seconds * 1000L;
            this.cfg.set("Mutes." + uuid + ".time", time);
            this.cfg.set("Mutes." + uuid + ".reason", reason);
            this.saveFile(); // This will now be async
        }
    }

    public void sendMuteMessage(Player p) {
        p.sendMessage("");
        p.sendMessage(" §cDu bist im Moment stumm geschalten!");
        p.sendMessage("  §8• §fStummgeschalten bis: §7" + this.getUnmuteDate(p.getUniqueId().toString()));
        p.sendMessage("  §8• §fGrund der Stummschaltung: §c" + this.getReason(p.getUniqueId().toString()));
        p.sendMessage("");
    }

    public String getReason(String uuid) {
        return this.cfg.getString("Mutes." + uuid + ".reason");
    }

    public String getUnmuteDate(String uuid) {
        long time = this.cfg.getLong("Mutes." + uuid + ".time");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return sdf.format(new Date(time));
    }

    public void unmute(UUID uuid) {
        this.cfg.set("Mutes." + uuid + ".time", (Object)null);
        this.cfg.set("Mutes." + uuid + ".reason", (Object)null);
        this.saveFile(); // This will now be async
    }

    public long getMuteMillis(String uuid) {
        return this.cfg.getLong("Mutes." + uuid + ".time");
    }

    public boolean isMuted(String uuid) {
        return this.cfg.contains("Mutes." + uuid + ".reason");
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