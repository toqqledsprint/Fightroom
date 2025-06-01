package de.fightroom.punish;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KickCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length >= 3) {
                if (Bukkit.getPlayerExact(args[0]) !=null) {
                    String reason = args[1];
                    Player target = Bukkit.getPlayerExact(args[0]);
                    player.sendMessage("§7Du hast §7" + target.getName() + " §7für §c" + reason.toUpperCase() + " §7gekickt!");
                    String proof1 = "";
                    for (int i = 4; i < args.length; i++) {
                        proof1 += args[i] + " ";
                    }

                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(" §c§lKICK §8- §7" + target.getName() + " §7wurde gekickt!");
                    Bukkit.broadcastMessage(" §8» §7Grund: §c" + reason.toUpperCase());
                    Bukkit.broadcastMessage("");
                    target.kickPlayer("§cDu wurdest für §e" + reason + " §cgekickt!\n\n");

                }
            } else {
                player.sendMessage("§cVerwende: §7/kick <Name> <Grund> <Beweis>");
            }

        } else {
            sender.sendMessage("§cDu musst ein Spieler sein!");
        }
        return true;
    }

    private static final List<String> COMMANDS_REASONS = Arrays.asList("BELEIDIGUNG", "FEINDLICHKEIT", "SPAM", "WERBUNG", "OBSZÖNITÄT", "ECHTGELDHANDEL");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>());
        }
        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], COMMANDS_REASONS, new ArrayList<>());
        }

        return null;
    }
}

