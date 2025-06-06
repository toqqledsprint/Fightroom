package de.fightroom.punish;

import de.fightroom.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.List;

public class AltsCommand implements CommandExecutor {

    private final Main plugin;
    private final File dataFile;
    private final FileConfiguration data;

    public AltsCommand(Main plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "alts.yml");
        this.data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§cBenutze: §7/alts <Name>");
        }

        if (args.length == 1) {
            if (sender instanceof Player) {
                if (Bukkit.getPlayerExact(args[0]) != null) {
                    Player player = Bukkit.getPlayerExact(args[0]);
                    String playerName = player.getName();
                    String ip = player.getAddress().getAddress().getHostAddress();

                    List<String> knownPlayers = data.getStringList(ip);

                    // Nur Altaccounts melden, wenn es mehr als 1 Spieler mit dieser IP gibt
                    if (knownPlayers.size() > 1) {
                        StringBuilder altAccounts = new StringBuilder();

                        for (String name : knownPlayers) {
                            if (!name.equalsIgnoreCase(playerName)) {
                                OfflinePlayer known = Bukkit.getOfflinePlayer(name);
                                boolean isBanned = Main.getInstance().getBans().isBanned(known.getUniqueId().toString());

                                if (isBanned) {
                                    altAccounts.append("§c§l").append(name).append(" (GESPERRT)").append(ChatColor.RESET);
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

                                player.sendMessage(message);
                            }
                        }
                    }
                } else {
                    sender.sendMessage("§cDieser Spieler ist nicht online!");
                }
            } else {
                sender.sendMessage("§cDu musst ein Spieler sein!");
            }
        }
        return true;
    }
}
