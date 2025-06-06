package de.fightroom.punish;

import de.fightroom.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AltChecker implements Listener {

    private final Main plugin;
    private File dataFile;
    private FileConfiguration data;

    public AltChecker(Main plugin) {
        this.plugin = plugin;
        setupDataFile();
    }

    private void setupDataFile() {
        dataFile = new File(plugin.getDataFolder(), "alts.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String ip = player.getAddress().getAddress().getHostAddress();

        List<String> knownPlayers = data.getStringList(ip);

        if (!knownPlayers.contains(playerName)) {
            knownPlayers.add(playerName);
            data.set(ip, knownPlayers);
            saveData();
        }

        // Nur Altaccounts melden, wenn es mehr als 1 Spieler mit dieser IP gibt
        if (knownPlayers.size() > 1) {
            StringBuilder altAccounts = new StringBuilder();

            for (String name : knownPlayers) {
                if (!name.equalsIgnoreCase(playerName)) {
                    OfflinePlayer known = Bukkit.getOfflinePlayer(name);
                    boolean isBanned = Main.getInstance().getBans().isBanned(known.getUniqueId().toString());

                    if (isBanned) {
                        if (Main.getInstance().getBans().getBanMillis(player.getUniqueId().toString()) <= System.currentTimeMillis()) {
                            Main.getInstance().getBans().unban(player.getUniqueId());
                        } else {
                            altAccounts.append("§c§l").append(name).append(" (GESPERRT)").append(ChatColor.RESET);
                        }
                    } else {
                        altAccounts.append(ChatColor.GREEN).append(name).append(ChatColor.RESET);
                    }

                    altAccounts.append(", ");
                }
            }

            if (!altAccounts.toString().isEmpty()) {
                if (altAccounts.length() > 0) {
                    String message = "§8[§cFightroom§8] §7" + "Der Spieler §a" + playerName +
                            " §7könnte ein Multiaccount sein von: " +
                            altAccounts.substring(0, altAccounts.length() - 2);


                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.hasPermission("fightroom.team")) {
                            onlinePlayer.sendMessage(message);
                        }
                    }
                }
            }
        }
    }

    private void saveData() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                data.save(dataFile);
            } catch (IOException e) {
                System.out.println("Couldn't save file: " + dataFile.getName()); // More descriptive error
                e.printStackTrace();
            }
        });
    }
}