package de.fightroom;

import de.fightroom.punish.*;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public final class Main extends JavaPlugin implements Listener {

    private static Main instance;

    private Bans bans;
    private Mutes mutes;

    private final List<String> broadcasts = Arrays.asList(
            "\n§7Vote immer für unseren Server §8► §b§l*/VOTE*\n",
            "\n§bFolge uns auf Discord für Updates! §8► §b/dc\n",
            "\n§cUnser Regelwerk §8► §c/regeln\n"
    );

    private final Random random = new Random();

    @Override
    public void onEnable() {

        instance = this;
        this.bans = new Bans(this);
        this.mutes = new Mutes(this);

        File folder = new File(getDataFolder(), "");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        Reports.setup();
        InfoConfig.setup();

        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("§5§l[FIGHTROOM-UTILS]");
        console.sendMessage("§5§l[FIGHTROOM-UTILS] §9Plugin §7erfolgreich §8▸ §aaktiviert");
        console.sendMessage("§5§l[FIGHTROOM-UTILS]");

        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new Unban());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new Unmute());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("report").setExecutor(new Report(this));
        getCommand("userinfo").setExecutor(new Userinfo(this));
        getCommand("regeln").setExecutor(new Regeln(this));
        getCommand("alts").setExecutor(new AltsCommand(this));

        getServer().getPluginManager().registerEvents(new Report(this), this);
        getServer().getPluginManager().registerEvents(new Userinfo(this), this);
        getServer().getPluginManager().registerEvents(new AltChecker(this), this);
        getServer().getPluginManager().registerEvents(this, this);

        if (!broadcasts.isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    String message = broadcasts.get(random.nextInt(broadcasts.size()));
                    Bukkit.broadcastMessage(message);
                }
            }.runTaskTimer(Main.getInstance(), 2400, 8400);
        } else {
            Bukkit.getLogger().warning("KEINE BROADCAST NACHRICHT GESENDET");
        }

    }

    public static Main getInstance() {
        return instance;
    }

    public Bans getBans() {
        return bans;
    }

    public Mutes getMutes() {
        return mutes;
    }

    @EventHandler
    public void onJoinIsBanned(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        if (getBans().isBanned(player.getUniqueId().toString())) {
            if (getBans().getBanMillis(player.getUniqueId().toString()) <= System.currentTimeMillis()) {
                getBans().unban(player.getUniqueId());
            } else {
                getBans().sendBanScreen(player);
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                e.setKickMessage(getBans().sendBanScreenString(e.getPlayer()));
            }
        }
    }

    @EventHandler
    public void onChatMessageisMuted(AsyncPlayerChatEvent e) {
        if (getMutes().isMuted(e.getPlayer().getUniqueId().toString())) {
            if (getMutes().getMuteMillis(e.getPlayer().getUniqueId().toString()) <= System.currentTimeMillis()) {
                getMutes().unmute(e.getPlayer().getUniqueId());
            } else {
                e.setCancelled(true);
                getMutes().sendMuteMessage(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onMuteMsg(PlayerCommandPreprocessEvent event) {
        if (getMutes().isMuted(event.getPlayer().getUniqueId().toString())) {
            String message = event.getMessage().toLowerCase();

            if (message.startsWith("/msg") || message.startsWith("/r ")) {
                getMutes().sendMuteMessage(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    public void sendDiscordMessage(String webhookURL, String message) {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            DiscordWebhook discordWebhook = new DiscordWebhook(webhookURL);
            discordWebhook.setContent("``` " +
                    message.replaceAll("`", " ")
                            .replaceAll("\"", " ")
                            .replaceAll("@everyone", "@ everyone")
                            .replaceAll("@here", "@ here") +
                    " ```");
            try {
                discordWebhook.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void sendDiscordEmbed(String webhookURL, String author, String authorURL, String authorIcon, String title, String color, String thumbnailURL, String footer, String footerIcon, String description) {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            DiscordWebhook discordWebhook = new DiscordWebhook(webhookURL);
            discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setAuthor(author, authorURL, authorIcon)
                    .setTitle(title)
                    .setColor(Color.decode(color))
                    .setThumbnail(thumbnailURL)
                    .setFooter(footer, footerIcon)
                    .setDescription(description));
            try {
                discordWebhook.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


}


