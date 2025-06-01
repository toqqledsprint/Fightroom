package de.fightroom;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.SkinParts;
import com.destroystokyo.paper.ClientOption.ChatVisibility;
import com.destroystokyo.paper.event.player.PlayerClientOptionsChangeEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.MainHand;

public class Info implements CommandExecutor, Listener {
    private final Main plugin;

    public Info(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (command.getName().equalsIgnoreCase("info")) {
                if (args.length == 0) {
                    player.sendMessage("§cVerwende: §7/info <Spieler>");
                }

                if (args.length == 1) {
                    String skinraw;
                    String hats;
                    String capes;
                    String jacket;
                    String right_sleeve;
                    String left_sleeve;
                    String right_pants;
                    String left_pants;
                    String view_distance;
                    if (Bukkit.getPlayerExact(args[0]) != null) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        skinraw = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).getRaw());
                        hats = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasHatsEnabled());
                        capes = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasCapeEnabled());
                        jacket = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasJacketEnabled());
                        right_sleeve = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasRightSleeveEnabled());
                        left_sleeve = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasLeftSleeveEnabled());
                        right_pants = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasRightPantsEnabled());
                        left_pants = String.valueOf(((SkinParts)target.getClientOption(ClientOption.SKIN_PARTS)).hasLeftPantsEnabled());
                        view_distance = target.getGameMode().toString().replaceAll("SURVIVAL", ChatColor.of("#f01111") + "Überlebensmodus").replaceAll("CREATIVE", ChatColor.of("#05ffcd") + "Kreativmodus").replaceAll("SPECTATOR", ChatColor.of("#00bbff") + "Zuschauermodus").replaceAll("ADVENTURE", ChatColor.of("#eaff05") + "Abenteuermodus");
                        TextComponent clientsetting = new TextComponent("§eClient-Kürzel:");
                        TextComponent clienthover = new TextComponent("§7 " + target.getClientOption(ClientOption.VIEW_DISTANCE) + ((String)target.getClientOption(ClientOption.LOCALE)).replaceAll("_", "-") + ((Boolean)target.getClientOption(ClientOption.CHAT_COLORS_ENABLED)).toString().replaceAll("true", "1").replaceAll("false", "0") + ((MainHand)target.getClientOption(ClientOption.MAIN_HAND)).toString().replaceAll("RIGHT", "1").replaceAll("LEFT", "0") + skinraw.replaceAll("raw=", ""));
                        clientsetting.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§eSichtweite: §7" + target.getClientOption(ClientOption.VIEW_DISTANCE) + "\n§eSprache: §7" + (String)target.getClientOption(ClientOption.LOCALE) + "\n§eChatsichtbarkeit: §7" + ((ChatVisibility)target.getClientOption(ClientOption.CHAT_VISIBILITY)).toString().replaceAll("FULL", "§atrue").replaceAll("SYSTEM", "§7only command").replaceAll("HIDDEN", "§cfalse") + "\n§eChatfarben: §7" + ((Boolean)target.getClientOption(ClientOption.CHAT_COLORS_ENABLED)).toString().replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eHaupthand: §7" + ((MainHand)target.getClientOption(ClientOption.MAIN_HAND)).toString().replaceAll("RIGHT", "Rechts").replaceAll("LEFT", "Links") + "\n§eHut: §7" + hats.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eUmhang: §7" + capes.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eJacke: §7" + jacket.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eRechter Ärmel: §7" + right_sleeve.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eLinker Ärmel: §7" + left_sleeve.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eRechtes Hosenbein: §7" + right_pants.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eLinkes Hosenbein: §7" + left_pants.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eChatfilterung: §7" + ((Boolean)target.getClientOption(ClientOption.TEXT_FILTERING_ENABLED)).toString().replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eAuflistung in Spielervorschau: §7" + ((Boolean)target.getClientOption(ClientOption.ALLOW_SERVER_LISTINGS)).toString().replaceAll("true", "§atrue").replaceAll("false", "§cfalse"))));
                        clientsetting.addExtra(clienthover);
                        TextComponent playername = new TextComponent(ChatColor.of("#ff8c00") + "§6§lInformationen über");
                        TextComponent playerhover = new TextComponent("§7§l " + target.getName());
                        playerhover.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§eSpielmodus: " + view_distance + "\n§7Ping: " + target.getPing() + "\n§7Effekte: " + target.getActivePotionEffects())));
                        playername.addExtra(playerhover);
                        player.sendMessage(playername);
                        TextComponent uuidfirst = new TextComponent("§eUUID:");
                        TextComponent uuid = new TextComponent("§7 " + target.getUniqueId());
                        uuid.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.COPY_TO_CLIPBOARD, "" + target.getUniqueId()));
                        uuid.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§aKlicke, um die UUID zu kopieren")));
                        uuidfirst.addExtra(uuid);
                        player.sendMessage(uuidfirst);
                        player.sendMessage("§eClient: §7" + target.getClientBrandName());
                        player.sendMessage(clientsetting);
                        player.sendMessage("§eSichtweite: §7" + target.getClientOption(ClientOption.VIEW_DISTANCE));
                        player.sendMessage("§eChatsichtbarkeit: " + ((ChatVisibility)target.getClientOption(ClientOption.CHAT_VISIBILITY)).toString().replaceAll("FULL", "§atrue").replaceAll("SYSTEM", "§7only command").replaceAll("HIDDEN", "§cfalse"));
                        player.sendMessage("§eChatfarben: " + ((Boolean)target.getClientOption(ClientOption.CHAT_COLORS_ENABLED)).toString().replaceAll("true", "§atrue").replaceAll("false", "§cfalse"));
                        player.sendMessage("§eSprache: §7" + ((String)target.getClientOption(ClientOption.LOCALE)).replaceAll("de_de", "Deutsch (Deutschland)").replaceAll("de_at", "Deutsch (Österreich)").replaceAll("de_ch", "Deutsch (Schweiz)").replaceAll("en_gb", "Englisch (Vereinigtes Königreich)").replaceAll("en_us", "Englisch (Vereinigte Staaten)"));
                        String[] parts = target.getAddress().toString().replaceAll("/", "").split(":");
                        if (this.plugin.getMutes().isMuted(target.getUniqueId().toString())) {
                            if (this.plugin.getMutes().getMuteMillis(target.getUniqueId().toString()) <= System.currentTimeMillis()) {
                                this.plugin.getMutes().unmute(target.getUniqueId()); // Calls Mutes.saveFile (now async)
                            } else {
                                player.sendMessage("§cStummgeschalten bis: §7" + this.plugin.getMutes().getUnmuteDate(target.getUniqueId().toString()));
                                player.sendMessage("§cGrund: §7" + this.plugin.getMutes().getReason(target.getUniqueId().toString()));
                            }
                        }
                    } else {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                        skinraw = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_raw");
                        hats = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_hats");
                        capes = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_cape");
                        jacket = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_jacket");
                        right_sleeve = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_rightsleeve");
                        left_sleeve = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_leftsleeve");
                        right_pants = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_rightpants");
                        left_pants = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_leftpants");
                        view_distance = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".viewdistance");
                        String locale = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".locale");
                        String chatcolors = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".chatcolors");
                        String mainhand = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".skin_mainhand");
                        String clientbrand = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".clientbrand");
                        String lastseen = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".lastseen");
                        if (InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".gamemode") != null) {
                            String gamemode = InfoConfig.get().getString(offlinePlayer.getUniqueId() + ".gamemode").replaceAll("SURVIVAL", ChatColor.of("#f01111") + "Überlebensmodus").replaceAll("CREATIVE", ChatColor.of("#05ffcd") + "Kreativmodus").replaceAll("SPECTATOR", ChatColor.of("#00bbff") + "Zuschauermodus").replaceAll("ADVENTURE", ChatColor.of("#eaff05") + "Abenteuermodus");
                            if (skinraw != null && hats != null && capes != null && jacket != null && right_sleeve != null && left_sleeve != null && left_pants != null && right_pants != null && view_distance != null && locale != null && chatcolors != null && mainhand != null && clientbrand != null && lastseen != null) {
                                if (InfoConfig.get().getString(String.valueOf(offlinePlayer.getUniqueId())) != null) {
                                    TextComponent clientsetting = new TextComponent("§eClient-Kürzel:");
                                    TextComponent clienthover = new TextComponent("§7 " + view_distance + locale.replaceAll("_", "-") + chatcolors.replaceAll("true", "1").replaceAll("false", "0") + mainhand.replaceAll("RIGHT", "1").replaceAll("LEFT", "0") + skinraw.replaceAll("raw=", ""));
                                    clientsetting.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§eSichtweite: §7" + view_distance + "\n§eSprache: §7" + locale + "\n§eChatfarben: §7" + chatcolors.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eHaupthand: §7" + mainhand.replaceAll("RIGHT", "Rechts").replaceAll("LEFT", "Links") + "\n§eHut: §7" + hats.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eUmhang: §7" + capes.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eJacke: §7" + jacket.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eRechter Ärmel: §7" + right_sleeve.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eLinker Ärmel: §7" + left_sleeve.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eRechtes Hosenbein: §7" + right_pants.replaceAll("true", "§atrue").replaceAll("false", "§cfalse") + "\n§eLinkes Hosenbein: §7" + left_pants.replaceAll("true", "§atrue").replaceAll("false", "§cfalse"))));
                                    clientsetting.addExtra(clienthover);
                                    TextComponent playername = new TextComponent(ChatColor.of("#ff8c00") + "§6§lInformationen über ");
                                    TextComponent playerhover = new TextComponent("§7" + offlinePlayer.getName());
                                    playerhover.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§eSpielmodus: " + gamemode)));
                                    playername.addExtra(playerhover);
                                    player.sendMessage(playername);
                                    TextComponent uuidfirst = new TextComponent("§eUUID:");
                                    TextComponent uuid = new TextComponent("§7 " + offlinePlayer.getUniqueId());
                                    uuid.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.COPY_TO_CLIPBOARD, "" + offlinePlayer.getUniqueId()));
                                    uuid.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§aKlicke, um die UUID zu kopieren")));
                                    uuidfirst.addExtra(uuid);
                                    player.sendMessage(uuidfirst);
                                    player.sendMessage("§eClient: §7" + clientbrand);
                                    player.sendMessage(clientsetting);
                                    player.sendMessage("§eSichtweite: §7" + view_distance);
                                    player.sendMessage("§eChatfarben: " + chatcolors.toString().replaceAll("true", "§atrue").replaceAll("false", "§cfalse"));
                                    player.sendMessage("§eSprache: §7" + locale.replaceAll("de_de", "Deutsch (Deutschland)").replaceAll("de_at", "Deutsch (Österreich)").replaceAll("de_ch", "Deutsch (Schweiz)").replaceAll("en_gb", "Englisch (Vereinigtes Königreich)").replaceAll("en_us", "Englisch (Vereinigte Staaten)"));
                                    player.sendMessage("§eOffline seit: §7" + lastseen);
                                    if (this.plugin.getMutes().isMuted(offlinePlayer.getUniqueId().toString())) {
                                        if (this.plugin.getMutes().getMuteMillis(offlinePlayer.getUniqueId().toString()) <= System.currentTimeMillis()) {
                                            this.plugin.getMutes().unmute(offlinePlayer.getUniqueId()); // Calls Mutes.saveFile (now async)
                                        } else {
                                            player.sendMessage("§cStummgeschalten bis: §7" + this.plugin.getMutes().getUnmuteDate(offlinePlayer.getUniqueId().toString()));
                                            player.sendMessage("§cGrund: §7" + this.plugin.getMutes().getReason(offlinePlayer.getUniqueId().toString()));
                                        }
                                    }

                                    if (this.plugin.getBans().isBanned(offlinePlayer.getUniqueId().toString())) {
                                        if (this.plugin.getBans().getBanMillis(offlinePlayer.getUniqueId().toString()) <= System.currentTimeMillis()) {
                                            this.plugin.getBans().unban(offlinePlayer.getUniqueId()); // Calls Bans.saveFile (now async)
                                        } else {
                                            player.sendMessage("§cGesperrt bis: §7" + this.plugin.getBans().getUnbanDate(offlinePlayer.getUniqueId().toString()));
                                            player.sendMessage("§cGrund: §7" + this.plugin.getBans().getReason(offlinePlayer.getUniqueId().toString()));
                                        }
                                    }
                                } else {
                                    player.sendMessage("§cEs konnten keine Informationen über den Spieler gefunden werden!");
                                }
                            } else {
                                player.sendMessage("§cEs konnten keine Informationen über den Spieler gefunden werden!");
                            }
                        } else {
                            player.sendMessage("§cEs konnten keine Informationen über den Spieler gefunden werden!");
                        }
                    }
                }
            }
        } else {
            sender.sendMessage("§cDu musst ein Spieler sein!");
        }

        return true;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            public void run() {
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".gamemode", e.getPlayer().getGameMode().toString());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".clientbrand", e.getPlayer().getClientBrandName());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".viewdistance", e.getPlayer().getViewDistance());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".chatcolors", e.getPlayer().getClientOption(ClientOption.CHAT_COLORS_ENABLED));
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".locale", e.getPlayer().getClientOption(ClientOption.LOCALE));
                String[] parts = e.getPlayer().getAddress().toString().replaceAll("/", "").split(":");
                String adress = parts[0];
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_mainhand", ((MainHand)e.getPlayer().getClientOption(ClientOption.MAIN_HAND)).toString());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_raw", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).getRaw());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_hats", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasHatsEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_cape", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasCapeEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_jacket", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasJacketEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_rightsleeve", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasRightSleeveEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_leftsleeve", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasLeftSleeveEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_rightpants", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasRightPantsEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_leftpants", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasLeftPantsEnabled());
                InfoConfig.save(plugin); // Pass the plugin instance
            }
        }, 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        InfoConfig.get().set(e.getPlayer().getUniqueId() + ".lastseen", this.getLastSeen());
        InfoConfig.save(plugin); // Pass the plugin instance
    }

    @EventHandler
    public void onClientOptionChange(final PlayerClientOptionsChangeEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            public void run() {
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".gamemode", e.getPlayer().getGameMode().toString());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".clientbrand", e.getPlayer().getClientBrandName());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".viewdistance", e.getPlayer().getViewDistance());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".chatcolors", e.getPlayer().getClientOption(ClientOption.CHAT_COLORS_ENABLED));
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".locale", e.getPlayer().getClientOption(ClientOption.LOCALE));
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_mainhand", ((MainHand)e.getPlayer().getClientOption(ClientOption.MAIN_HAND)).toString());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_raw", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).getRaw());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_hats", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasHatsEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_cape", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasCapeEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_jacket", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasJacketEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_rightsleeve", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasRightSleeveEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_leftsleeve", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasLeftSleeveEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_rightpants", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasRightPantsEnabled());
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".skin_leftpants", ((SkinParts)e.getPlayer().getClientOption(ClientOption.SKIN_PARTS)).hasLeftPantsEnabled());
                InfoConfig.save(plugin);
            }
        }, 20L);
    }

    @EventHandler
    public void onGameModeChange(final PlayerGameModeChangeEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            public void run() {
                InfoConfig.get().set(e.getPlayer().getUniqueId() + ".gamemode", e.getPlayer().getGameMode().toString());
                InfoConfig.save(plugin); // Pass the plugin instance
            }
        }, 20L);
    }

    private String getLastSeen() {
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        Date date = new Date(time);
        return sdf.format(date);
    }
}
