package de.fightroom;

import de.fightroom.punish.*;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public final class Main extends JavaPlugin {

    private static Main instance;

    private Bans bans;
    private Mutes mutes;

    private final List<String> broadcasts = Arrays.asList(
            "\n§7Vote immer für unseren Server §8► §b§l*/VOTE*\n",
            "\n§bFolge uns auf Discord für Updates! §8► §b/dc\n",
            "\n§cUnser Regelwerk §8► §c/regeln"
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
        getCommand("info").setExecutor(new Info(this));
        getCommand("regeln").setExecutor(new Regeln(this));

        getServer().getPluginManager().registerEvents(new Report(this), this);
        getServer().getPluginManager().registerEvents(new Info(this), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                String message = broadcasts.get(random.nextInt(broadcasts.size()));
                Bukkit.broadcastMessage(message);
            }
        }.runTaskTimer(Main.getInstance(), 2400, 8400);

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

}


