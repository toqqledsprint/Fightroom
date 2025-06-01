package de.fightroom.punish;

import de.fightroom.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MuteCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length >= 5) {
                String name = args[0];
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                String uuid = String.valueOf(target.getUniqueId());

                if (Main.getInstance().getMutes().isMuted(uuid)) {
                    Main.getInstance().getMutes().unmute(UUID.fromString(uuid));
                    long value1 = 0;
                    try {
                        value1 = Integer.valueOf(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("§cBitte gib nach dem Namen eine Dauer in Nummern an!");
                    }

                    String unitString1 = args[2];
                    String reason1 = args[3];

                    List<String> unitList1 = MuteUnit.getUnitsAsString();
                    if (unitList1.contains(unitString1.toLowerCase())) {

                        MuteUnit unit1 = MuteUnit.getUnit(unitString1);
                        long seconds = value1 * unit1.getToSecond();
                        Main.getInstance().getMutes().mute(uuid, reason1, seconds);
                        player.sendMessage("§7Du hast " + target.getName() + " §7für §e" + reason1.toUpperCase() + " §c" + value1 + " " + unit1.getName() + " §7gemutet!");
                        String proof1 = "";
                        for (int i = 4; i < args.length; i++) {
                            proof1 += args[i] + " ";
                        }

                        if (Bukkit.getPlayerExact(name) != null) {
                            Main.getInstance().getMutes().sendMuteMessage(Bukkit.getPlayerExact(name));
                        }
                        return true;
                    }
                }

                long value = 0;
                try {
                    value = Integer.valueOf(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cBitte gib nach dem Namen eine Dauer in Nummern an!");
                }

                String unitString = args[2];
                String reason = args[3];

                List<String> unitList = MuteUnit.getUnitsAsString();
                if (unitList.contains(unitString.toLowerCase())) {

                    MuteUnit unit = MuteUnit.getUnit(unitString);
                    long seconds = value * unit.getToSecond();
                    Main.getInstance().getMutes().mute(uuid, reason, seconds);
                    player.sendMessage("§7Du hast §7" + target.getName() + " §7für §e" + reason.toUpperCase() + " §c" + value + " " + unit.getName() + " §7gemutet!");
                    String proof = "";
                    for (int i = 4; i < args.length; i++) {
                        proof += args[i] + " ";
                    }

                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(" §c§lMUTE §8- §7" + target.getName() + " §7wurde stummgeschalten!");
                    Bukkit.broadcastMessage("  §8» §7Dauer: §e" + value + " " + unit.getName() + " §8| §7Grund: §c" + reason.toUpperCase());
                    Bukkit.broadcastMessage("");

                    if (Bukkit.getPlayerExact(name) != null) {
                        Main.getInstance().getMutes().sendMuteMessage(Bukkit.getPlayerExact(name));
                    }
                }
            } else {
                player.sendMessage("§cVerwende: §7/mute <Name> <Value> <MuteUnit> <Grund> <Beweis>");
            }

        } else {
            sender.sendMessage("§cDu musst ein Spieler sein!");
        }
        return true;
    }

    private static final List<String> COMMANDS_TIMES = Arrays.asList("sec", "min", "hour", "day", "week", "month", "year");
    private static final List<String> COMMANDS_REASONS = Arrays.asList("BELEIDIGUNG", "FEINDLICHKEIT", "SPAM", "WERBUNG", "OBSZÖNITÄT", "PROVOKATION");

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
        if (args.length == 3) {
            return StringUtil.copyPartialMatches(args[2], COMMANDS_TIMES, new ArrayList<>());
        }
        if (args.length == 4) {
            return StringUtil.copyPartialMatches(args[3], COMMANDS_REASONS, new ArrayList<>());
        }

        return null;
    }

}
