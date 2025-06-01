package de.fightroom.punish;

import de.fightroom.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Unmute implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                UUID uuid = offlinePlayer.getUniqueId();

                if (Main.getInstance().getMutes().isMuted(String.valueOf(uuid))) {
                    Main.getInstance().getMutes().unmute(uuid);
                    player.sendMessage("§7" + Bukkit.getOfflinePlayer(args[0]).getName() + " §cwurde entmutet!");
                } else {
                    player.sendMessage("§cDer Spieler ist nicht gemutet!");
                }

            }
        } else {
            sender.sendMessage("§cDu musst ein Spieler sein!");
        }
        return true;
    }
}
