package de.fightroom;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Regeln implements CommandExecutor {

    private final Main plugin;

    public Regeln(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent c = new TextComponent(" §8► ");
            TextComponent clickme = new TextComponent("§7§o*HIER KLICKEN*");
            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://fightroom-2ye.pages.dev/"));
            c.addExtra(clickme);
            player.sendMessage("");
            player.sendMessage(" §8► " + ChatColor.of("#f50000") + "§lUnsere Regeln:");
            player.sendMessage(c);
            player.sendMessage("");
        } else {
            sender.sendMessage("§cDu musst ein Spieler sein!");
        }
        return false;
    }
}
