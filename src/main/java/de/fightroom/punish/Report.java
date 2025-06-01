package de.fightroom.punish;

import de.fightroom.Main;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Report implements CommandExecutor, Listener {
    private final Main plugin;

    public Report(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("report")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cDu musst ein Spieler sein!");
                return true;
            }

            Player player = (Player)sender;
            if (args.length == 0) {
                player.sendMessage("§cVerwende: §7/report <Spieler>");
            }

            if (args.length == 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (!args[0].equals(player.getName())) {
                    if (target != null) {
                        if (!target.hasPermission("fightroom.team")) {
                            this.setReportedPlayer(player, target); // Calls Reports.save(plugin)
                            player.openInventory(this.MainInventory(player)); // Calls setReportPage and Reports.save(plugin)
                        } else {
                            player.sendMessage("§cDu kannst keine Teammitglieder melden!");
                        }
                    } else {
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                } else {
                    player.sendMessage("§cDas ergibt keinen Sinn!");
                }
            }
        }

        return true;
    }

    private void setReportPage(Player player, String page) {
        Reports.get().set(player.getUniqueId() + ".reportpage", page);
        Reports.save(plugin); // Pass the plugin instance
    }

    private String getReportPage(Player player) {
        if (Reports.get().getString(player.getUniqueId() + ".reportpage") != null) {
            String page = Reports.get().getString(player.getUniqueId() + ".reportpage");
            return page;
        } else {
            return null;
        }
    }

    private void setReportedPlayer(Player player, Player reported) {
        Reports.get().set(player.getUniqueId() + ".reported", reported.getUniqueId().toString());
        Reports.save(plugin); // Pass the plugin instance
    }

    private String getReportedPlayer(Player player) {
        if (Reports.get().getString(player.getUniqueId() + ".reported") != null) {
            String reported = Reports.get().getString(player.getUniqueId() + ".reported");
            return reported;
        } else {
            return null;
        }
    }

    private void setReason(Player player, String reason) {
        Reports.get().set(player.getUniqueId() + ".reason", reason);
        Reports.save(plugin); // Pass the plugin instance
    }

    private String getReason(Player player) {
        if (Reports.get().getString(player.getUniqueId() + ".reason") != null) {
            String reason = Reports.get().getString(player.getUniqueId() + ".reason");
            return reason;
        } else {
            return null;
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getView().getTitle().contains("§8Spieler melden") && e.getInventory() == e.getClickedInventory()) {
            e.setCancelled(true);
            // Removed redundant getReportPage() == "page" checks before setting page
            switch(e.getSlot()) {
                case 10:
                    // if (!this.getReportPage(player).equals("hacking")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.HackingInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // } else { // Removed else block
                    //   player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 5.0F, 1.0F);
                    // }
                    break;
                case 11:
                    // if (!this.getReportPage(player).equals("offense")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.OffenseInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // }
                    break;
                case 12:
                    // if (!this.getReportPage(player).equals("spam")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.SpamInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // }
                    break;
                case 13:
                    // if (!this.getReportPage(player).equals("advertise")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.AdvertiseInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // }
                    break;
                case 14:
                    // if (!this.getReportPage(player).equals("obscenity")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.ObscenityInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // }
                    break;
                case 15:
                    // if (!this.getReportPage(player).equals("exploitation")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.ExploitationInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // }
                    break;
                case 16:
                    // if (!this.getReportPage(player).equals("teaming")) { // Removed check
                    if (this.getReportedPlayer(player) != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                        player.openInventory(this.TeamingInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                    // }
            }

            // The checks for getReportPage().equals(...) before setting the reason are necessary
            // because the click handler covers all slots in the inventory, including the main menu bar.
            // We only want to set the reason if the player is currently on a specific reason page.
            if (this.getReportPage(player) != null) { // Added null check
                if (this.getReportPage(player).equals("hacking")) {
                    switch(e.getSlot()) {
                        case 28:
                            this.setReason(player, "Hacking <Killaura>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 29:
                            this.setReason(player, "Hacking <Autoklicker>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 30:
                            this.setReason(player, "Hacking <Fly>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 31:
                            this.setReason(player, "Hacking <Speed>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 32:
                            this.setReason(player, "Hacking <Range>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 33:
                            this.setReason(player, "Hacking <Angelbot>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 34:
                            this.setReason(player, "Hacking <Anderes>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("offense")) {
                    switch(e.getSlot()) {
                        case 30:
                            this.setReason(player, "Beleidigung <Öff. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 31:
                            this.setReason(player, "Beleidigung <Priv. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 32:
                            this.setReason(player, "Beleidigung <Anderes>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("spam")) {
                    switch(e.getSlot()) {
                        case 30:
                            this.setReason(player, "Spam <Öff. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 32:
                            this.setReason(player, "Spam <Priv. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("advertise")) {
                    switch(e.getSlot()) {
                        case 30:
                            this.setReason(player, "Werbung <Öff. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 31:
                            this.setReason(player, "Werbung <Priv. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 32:
                            this.setReason(player, "Werbung <Anderes>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("obscenity")) {
                    switch(e.getSlot()) {
                        case 30:
                            this.setReason(player, "Obszönität <Öff. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 31:
                            this.setReason(player, "Obszönität <Priv. Chat>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 32:
                            this.setReason(player, "Obszönität <Skin / Spielername>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("exploitation")) {
                    switch(e.getSlot()) {
                        case 30:
                            this.setReason(player, "Bug-Ausnutzung <Duplizierungsbug>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 32:
                            this.setReason(player, "Bug-Ausnutzung <Anderes>"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("teaming")) {
                    switch(e.getSlot()) {
                        case 31:
                            this.setReason(player, "Teaming"); // Calls Reports.save(plugin)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5.0F, 1.0F);
                            player.openInventory(this.ConfirmInventory(player)); // Calls setReportPage and Reports.save(plugin)
                    }
                } else if (this.getReportPage(player).equals("confirm")) { // Added check for confirm page
                    switch(e.getSlot()) {
                        case 0:
                            player.openInventory(this.MainInventory(player)); // Calls setReportPage and Reports.save(plugin)
                            break;
                        case 4:
                            if (this.getReportedPlayer(player) != null) {
                                player.closeInventory();
                                this.sendReport(player);
                                // NOTE: Saving the final report data persistently is not done here.
                                // If needed, add Reports.get().set(...) and Reports.save(plugin) here
                                // *after* sending the message, and make sure Reports.setup() doesn't delete the file.
                            } else {
                                player.closeInventory();
                                player.sendMessage("§cDieser Spieler ist nicht online!");
                            }
                            break;
                    }
                }
            }
        }
        // Removed the redundant check below, handled in the main if block
        // if (e.getView().getTitle().contains("§8Spieler melden") && e.getInventory() == e.getClickedInventory() && this.getReportPage(player).equals("confirm")) {
        //     e.setCancelled(true);
        //     switch(e.getSlot()) {
        //     case 0:
        //        player.openInventory(this.MainInventory(player));
        //        break;
        //     case 4:
        //        if (this.getReportedPlayer(player) != null) {
        //           player.closeInventory();
        //           this.sendReport(player);
        //        } else {
        //           player.closeInventory();
        //           player.sendMessage("§cDieser Spieler ist nicht online!");
        //        }
        //     }
        // }
    }

    private ItemStack mainbar_playerhead(Player player) {
        ItemStack player_head = new ItemStack(Material.PLAYER_HEAD);
        if (Reports.get().getString(player.getUniqueId() + ".reported") != null) {
            Player target = Bukkit.getPlayer(UUID.fromString(Reports.get().getString(player.getUniqueId() + ".reported")));
            // Added null check for target player
            if (target != null) {
                SkullMeta itemmeta_playerhead = (SkullMeta)player_head.getItemMeta();
                itemmeta_playerhead.setOwningPlayer(target);
                itemmeta_playerhead.setDisplayName("§e" + target.getName());
                player_head.setItemMeta(itemmeta_playerhead);
                return player_head;
            }
        }
        // Return a default head or null if target is not online or not set
        SkullMeta itemmeta_playerhead = (SkullMeta)player_head.getItemMeta();
        itemmeta_playerhead.setDisplayName("§cSpieler nicht gefunden");
        player_head.setItemMeta(itemmeta_playerhead);
        return player_head;
    }


    private ItemStack mainbar_hacking(boolean selected) {
        ItemStack ironsword = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemmeta_ironsword = ironsword.getItemMeta();
        itemmeta_ironsword.setDisplayName("§cHacking");
        itemmeta_ironsword.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_ironsword.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_ironsword = new ArrayList();
        itemlore_ironsword.add(" §8► §fDer Spieler verwendet einen Hack.");
        itemlore_ironsword.add("");
        if (!selected) {
            itemlore_ironsword.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_ironsword.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_ironsword.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_ironsword.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_ironsword.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_ironsword.setLore(itemlore_ironsword);
        ironsword.setItemMeta(itemmeta_ironsword);
        return ironsword;
    }

    private ItemStack mainbar_offense(boolean selected) {
        ItemStack pufferfish = new ItemStack(Material.PUFFERFISH);
        ItemMeta itemmeta_pufferfish = pufferfish.getItemMeta();
        itemmeta_pufferfish.setDisplayName("§6Beleidigung");
        ArrayList<String> itemlore_pufferfish = new ArrayList();
        itemlore_pufferfish.add(" §8► §fDer Spieler beleidigt");
        itemlore_pufferfish.add("   §fandere Spieler.");
        itemlore_pufferfish.add("");
        if (!selected) {
            itemlore_pufferfish.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_pufferfish.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_pufferfish.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_pufferfish.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_pufferfish.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_pufferfish.setLore(itemlore_pufferfish);
        pufferfish.setItemMeta(itemmeta_pufferfish);
        return pufferfish;
    }

    private ItemStack mainbar_spam(boolean selected) {
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta itemmeta_book = book.getItemMeta();
        itemmeta_book.setDisplayName("§cSpam");
        ArrayList<String> itemlore_book = new ArrayList();
        itemlore_book.add(" §8► §fDer Spieler spammt den Chat voll.");
        itemlore_book.add("");
        if (!selected) {
            itemlore_book.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_book.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_book.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_book.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_book.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_book.setLore(itemlore_book);
        book.setItemMeta(itemmeta_book);
        return book;
    }

    private ItemStack mainbar_advertise(boolean selected) {
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemmeta_sign = sign.getItemMeta();
        itemmeta_sign.setDisplayName("§eWerbung");
        ArrayList<String> itemlore_sign = new ArrayList();
        itemlore_sign.add(" §8► §fDer Spieler wirbt für einen");
        itemlore_sign.add("   §fanderen Server oder eine");
        itemlore_sign.add("   §funangebrachte Webseite.");
        itemlore_sign.add("");
        if (!selected) {
            itemlore_sign.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_sign.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_sign.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_sign.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_sign.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_sign.setLore(itemlore_sign);
        sign.setItemMeta(itemmeta_sign);
        return sign;
    }

    private ItemStack mainbar_obscenity(boolean selected) {
        ItemStack egg = new ItemStack(Material.EGG);
        ItemMeta itemmeta_egg = egg.getItemMeta();
        itemmeta_egg.setDisplayName("§cObszönität");
        ArrayList<String> itemlore_egg = new ArrayList();
        itemlore_egg.add(" §8► §fDer Spieler löst durch seine");
        itemlore_egg.add("   §fÄußerungen Scham oder");
        itemlore_egg.add("   §fEkel bei anderen aus.");
        itemlore_egg.add("");
        if (!selected) {
            itemlore_egg.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_egg.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_egg.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_egg.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_egg.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_egg.setLore(itemlore_egg);
        egg.setItemMeta(itemmeta_egg);
        return egg;
    }

    private ItemStack mainbar_exploitation(boolean selected) {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta itemmeta_barrier = barrier.getItemMeta();
        itemmeta_barrier.setDisplayName("§cBug-Ausnutzung");
        ArrayList<String> itemlore_barrier = new ArrayList();
        itemlore_barrier.add(" §8► §fDer Spieler nutzt einen Bug");
        itemlore_barrier.add("   §fund verschafft sich damit");
        itemlore_barrier.add("   §feinen Vorteil.");
        itemlore_barrier.add("");
        if (!selected) {
            itemlore_barrier.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_barrier.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_barrier.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_barrier.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_barrier.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_barrier.setLore(itemlore_barrier);
        barrier.setItemMeta(itemmeta_barrier);
        return barrier;
    }

    private ItemStack mainbar_teaming(boolean selected) {
        ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta itemmeta_axe = axe.getItemMeta();
        itemmeta_axe.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_axe.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        itemmeta_axe.setDisplayName("§cTeaming");
        ArrayList<String> itemlore_axe = new ArrayList();
        itemlore_axe.add(" §8► §fDer Spieler betreibt");
        itemlore_axe.add("   §funerlaubtes Teaming.");
        itemlore_axe.add("");
        if (!selected) {
            itemlore_axe.add(" §8• §7§oKlicke, um alle Gründe dieser");
            itemlore_axe.add("  §7§oKategorie aufzulisten.");
        } else {
            itemlore_axe.add(" §8• §b§oIm Moment ausgewählt!");
            itemmeta_axe.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            itemmeta_axe.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        itemmeta_axe.setLore(itemlore_axe);
        axe.setItemMeta(itemmeta_axe);
        return axe;
    }

    public static String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        Date date = new Date(time);
        return sdf.format(date);
    }

    private void sendReport(Player player) {
        player.sendTitle("§a§l✔", "§aReport abgesendet!");
        Player target = Bukkit.getPlayer(UUID.fromString(this.getReportedPlayer(player)));
        // Added null check for target player
        if (target == null) {
            player.sendMessage("§cCould not send report: Target player offline.");
            return;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 5.0F, 1.0F);
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player all = (Player)var3.next();
            if (all.hasPermission("fightroom.team")) {
                all.sendMessage("§c§lFIGHTROOM REPORT");
                all.sendMessage("§cGemeldeter Spieler§8: §7" + target.getName());
                all.sendMessage("§aMelder§8: §7" + player.getName());
                all.sendMessage("§eGrund§8: §c" + this.getReason(player));
                all.sendMessage("");
            }
        }
        // Optionally save report data here if Reports.setup doesn't delete it
        // Reports.get().set("reports." + UUID.randomUUID().toString() + ".reporter", player.getUniqueId().toString());
        // Reports.get().set("reports." + UUID.randomUUID().toString() + ".reported", target.getUniqueId().toString());
        // Reports.get().set("reports." + UUID.randomUUID().toString() + ".reason", getReason(player));
        // Reports.get().set("reports." + UUID.randomUUID().toString() + ".timestamp", getDate());
        // Reports.save(plugin); // Pass the plugin instance for async save

    }

    private Inventory MainInventory(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, "§8Spieler melden");
        this.setReportPage(player, "main"); // Calls Reports.save(plugin)
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(false));
        // player.openInventory(gui); // No need to open here, the command handler does it
        return gui;
    }

    private Inventory ConfirmInventory(Player player) {
        this.setReportPage(player, "confirm"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory((InventoryHolder)null, InventoryType.HOPPER, "§8Spieler melden");
        ItemStack redstoneblock = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta itemmeta_redstoneblock = redstoneblock.getItemMeta();
        itemmeta_redstoneblock.setDisplayName("§cZurük zur Auswahl");
        itemmeta_redstoneblock.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_redstoneblock.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        redstoneblock.setItemMeta(itemmeta_redstoneblock);
        ItemStack emeraldblock = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta itemmeta_emeraldblock = emeraldblock.getItemMeta();
        itemmeta_emeraldblock.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_emeraldblock.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        itemmeta_emeraldblock.setDisplayName("§aReport absenden");
        ArrayList<String> itemlore_emeraldblock = new ArrayList();
        itemlore_emeraldblock.add(" §8• §7Ausgewählter Grund:");
        itemlore_emeraldblock.add("   §8» §c" + this.getReason(player));
        itemlore_emeraldblock.add("");
        itemlore_emeraldblock.add("§8<§c§l!§8> §cBitte beachte, dass du");
        itemlore_emeraldblock.add("    §cnur mit einem triftigen Grund");
        itemlore_emeraldblock.add("    §creportest!");
        itemlore_emeraldblock.add("");
        itemlore_emeraldblock.add("§8<§c§l!§8> §cDer Missbrauch kann");
        itemlore_emeraldblock.add("    §czu einem Bann führen!");
        itemmeta_emeraldblock.setLore(itemlore_emeraldblock);
        emeraldblock.setItemMeta(itemmeta_emeraldblock);
        gui.setItem(0, redstoneblock);
        gui.setItem(2, this.mainbar_playerhead(player));
        gui.setItem(4, emeraldblock);
        return gui;
    }

    private Inventory HackingInventory(Player player) {
        this.setReportPage(player, "hacking"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack ironsword = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemmeta_ironsword = ironsword.getItemMeta();
        itemmeta_ironsword.setDisplayName("§cKillaura");
        itemmeta_ironsword.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_ironsword.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_ironsword = new ArrayList();
        itemlore_ironsword.add(" §8► §fDer Spieler greift automatisch");
        itemlore_ironsword.add("   §fSpieler in seinem Umfeld an.");
        itemmeta_ironsword.setLore(itemlore_ironsword);
        ironsword.setItemMeta(itemmeta_ironsword);
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta itemmeta_stick = stick.getItemMeta();
        itemmeta_stick.setDisplayName("§cAutoklicker");
        ArrayList<String> itemlore_stick = new ArrayList();
        itemlore_stick.add(" §8► §fDer Spieler klickt unnatürlich");
        itemlore_stick.add("   §foft und schnell.");
        itemmeta_stick.setLore(itemlore_stick);
        stick.setItemMeta(itemmeta_stick);
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta itemmeta_elytra = elytra.getItemMeta();
        itemmeta_elytra.setDisplayName("§eFly-Hack");
        ArrayList<String> itemlore_elytra = new ArrayList();
        itemlore_elytra.add(" §8► §fDer Spieler fliegt.");
        itemmeta_elytra.setLore(itemlore_elytra);
        elytra.setItemMeta(itemmeta_elytra);
        ItemStack rabbitfoot = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta itemmeta_rabbitfoot = rabbitfoot.getItemMeta();
        itemmeta_rabbitfoot.setDisplayName("§bSpeed-Hack");
        ArrayList<String> itemlore_rabbitfood = new ArrayList();
        itemlore_rabbitfood.add(" §8► §fDer Spieler bewegt sich");
        itemlore_rabbitfood.add("   §funnatürlich schnell.");
        itemmeta_rabbitfoot.setLore(itemlore_rabbitfood);
        rabbitfoot.setItemMeta(itemmeta_rabbitfoot);
        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta itemmeta_arrow = arrow.getItemMeta();
        itemmeta_arrow.setDisplayName("§cRange");
        ArrayList<String> itemlore_arrow = new ArrayList();
        itemlore_arrow.add(" §8► §fDer Spieler hat eine höhere");
        itemlore_arrow.add("   §fRange als normal.");
        itemmeta_arrow.setLore(itemlore_arrow);
        arrow.setItemMeta(itemmeta_arrow);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemMeta itemmeta_rod = rod.getItemMeta();
        itemmeta_rod.setDisplayName("§cAngelbot");
        ArrayList<String> itemlore_rod = new ArrayList();
        itemlore_rod.add(" §8► §fDer Spieler angelt, während");
        itemlore_rod.add("   §fdieser AFK ist.");
        itemmeta_rod.setLore(itemlore_rod);
        rod.setItemMeta(itemmeta_rod);
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemmeta_sign = sign.getItemMeta();
        itemmeta_sign.setDisplayName("§cAnderes");
        ArrayList<String> itemlore_sign = new ArrayList();
        itemlore_sign.add(" §8► §fDer Spieler nutzt einen");
        itemlore_sign.add("   §fanderen Hack.");
        itemmeta_sign.setLore(itemlore_sign);
        sign.setItemMeta(itemmeta_sign);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(true));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(false));
        gui.setItem(28, ironsword);
        gui.setItem(29, stick);
        gui.setItem(30, elytra);
        gui.setItem(31, rabbitfoot);
        gui.setItem(32, arrow);
        gui.setItem(33, rod);
        gui.setItem(34, sign);
        return gui;
    }

    private Inventory OffenseInventory(Player player) {
        this.setReportPage(player, "offense"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemmeta_map = map.getItemMeta();
        itemmeta_map.setDisplayName("§eÖffentlicher Chat");
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_map = new ArrayList();
        itemlore_map.add(" §8► §fDer Spieler beleidigt");
        itemlore_map.add("   §fim öffentlichen Chat.");
        itemmeta_map.setLore(itemlore_map);
        map.setItemMeta(itemmeta_map);
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta itemmeta_book = book.getItemMeta();
        itemmeta_book.setDisplayName("§bPrivater Chat");
        ArrayList<String> itemlore_book = new ArrayList();
        itemlore_book.add(" §8► §fDer Spieler beleidigt");
        itemlore_book.add("   §fim privaten Chat.");
        itemmeta_book.setLore(itemlore_book);
        book.setItemMeta(itemmeta_book);
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemmeta_sign = sign.getItemMeta();
        itemmeta_sign.setDisplayName("§cAnderes");
        ArrayList<String> itemlore_sign = new ArrayList();
        itemlore_sign.add(" §8► §fDer Spieler beleidigt");
        itemlore_sign.add("   §fz.B auf Items.");
        itemmeta_sign.setLore(itemlore_sign);
        sign.setItemMeta(itemmeta_sign);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(true));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(false));
        gui.setItem(30, map);
        gui.setItem(31, book);
        gui.setItem(32, sign);
        return gui;
    }

    private Inventory SpamInventory(Player player) {
        this.setReportPage(player, "spam"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemmeta_map = map.getItemMeta();
        itemmeta_map.setDisplayName("§eÖffentlicher Chat");
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_map = new ArrayList();
        itemlore_map.add(" §8► §fDer Spieler spammt im");
        itemlore_map.add("   §föffentlichen Chat.");
        itemmeta_map.setLore(itemlore_map);
        map.setItemMeta(itemmeta_map);
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta itemmeta_book = book.getItemMeta();
        itemmeta_book.setDisplayName("§bPrivater Chat");
        ArrayList<String> itemlore_book = new ArrayList();
        itemlore_book.add(" §8► §fDer Spieler spammt im");
        itemlore_book.add("   §fprivaten Chat.");
        itemmeta_book.setLore(itemlore_book);
        book.setItemMeta(itemmeta_book);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(true));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(false));
        gui.setItem(30, map);
        gui.setItem(32, book);
        return gui;
    }

    private Inventory AdvertiseInventory(Player player) {
        this.setReportPage(player, "advertise"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemmeta_map = map.getItemMeta();
        itemmeta_map.setDisplayName("§eÖffentlicher Chat");
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_map = new ArrayList();
        itemlore_map.add(" §8► §fDer Spieler macht im");
        itemlore_map.add("   §föffentlichen Chat Werbung.");
        itemmeta_map.setLore(itemlore_map);
        map.setItemMeta(itemmeta_map);
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta itemmeta_book = book.getItemMeta();
        itemmeta_book.setDisplayName("§bPrivater Chat");
        ArrayList<String> itemlore_book = new ArrayList();
        itemlore_book.add(" §8► §fDer Spieler macht im");
        itemlore_book.add("   §fprivaten Chat Werbung.");
        itemmeta_book.setLore(itemlore_book);
        book.setItemMeta(itemmeta_book);
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemmeta_sign = sign.getItemMeta();
        itemmeta_sign.setDisplayName("§cAnderes");
        ArrayList<String> itemlore_sign = new ArrayList();
        itemlore_sign.add(" §8► §fDer Spieler macht z.B.");
        itemlore_sign.add("   §fauf Items Werbung.");
        itemmeta_sign.setLore(itemlore_sign);
        sign.setItemMeta(itemmeta_sign);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(true));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(false));
        gui.setItem(30, map);
        gui.setItem(31, book);
        gui.setItem(32, sign);
        return gui;
    }

    private Inventory ObscenityInventory(Player player) {
        this.setReportPage(player, "obscenity"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemmeta_map = map.getItemMeta();
        itemmeta_map.setDisplayName("§eÖffentlicher Chat");
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_map.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_map = new ArrayList();
        itemlore_map.add(" §8► §fDer Spieler äußert");
        itemlore_map.add("   §fsich im öffentlichen");
        itemlore_map.add("   §fChat obszön.");
        itemmeta_map.setLore(itemlore_map);
        map.setItemMeta(itemmeta_map);
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta itemmeta_book = book.getItemMeta();
        itemmeta_book.setDisplayName("§bPrivater Chat");
        ArrayList<String> itemlore_book = new ArrayList();
        itemlore_book.add(" §8► §fDer Spieler äußert");
        itemlore_book.add("   §fsich im privaten");
        itemlore_book.add("   §fChat obszön.");
        itemmeta_book.setLore(itemlore_book);
        book.setItemMeta(itemmeta_book);
        ItemStack armorstand = new ItemStack(Material.ARMOR_STAND);
        ItemMeta itemmeta_armorstand = armorstand.getItemMeta();
        itemmeta_armorstand.setDisplayName("§6Skin oder Spielername");
        ArrayList<String> itemlore_armorstand = new ArrayList();
        itemlore_armorstand.add(" §8► §fDer Spieler hat einen obszönen");
        itemlore_armorstand.add("   §fSkin oder Spielernamen.");
        itemmeta_armorstand.setLore(itemlore_armorstand);
        armorstand.setItemMeta(itemmeta_armorstand);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(true));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(false));
        gui.setItem(30, map);
        gui.setItem(31, book);
        gui.setItem(32, armorstand);
        return gui;
    }

    private Inventory ExploitationInventory(Player player) {
        this.setReportPage(player, "exploitation"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack goldblock = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta itemmeta_goldblock = goldblock.getItemMeta();
        itemmeta_goldblock.setDisplayName("§eDuplizierungsbug");
        itemmeta_goldblock.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_goldblock.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_goldblock = new ArrayList();
        itemlore_goldblock.add(" §8► §fDer Spieler kann mit dem Bug Items");
        itemlore_goldblock.add("   §fduplizieren und nutzt diesen.");
        itemmeta_goldblock.setLore(itemlore_goldblock);
        goldblock.setItemMeta(itemmeta_goldblock);
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemmeta_sign = sign.getItemMeta();
        itemmeta_sign.setDisplayName("§cAnderes");
        ArrayList<String> itemlore_paper = new ArrayList();
        itemlore_paper.add(" §8► §fDer Spieler nutzt einen Bug aus und");
        itemlore_paper.add("   §fverschafft sich damit vorteile.");
        itemmeta_sign.setLore(itemlore_paper);
        sign.setItemMeta(itemmeta_sign);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(true));
        gui.setItem(16, this.mainbar_teaming(false));
        gui.setItem(30, goldblock);
        gui.setItem(32, sign);
        return gui;
    }

    private Inventory TeamingInventory(Player player) {
        this.setReportPage(player, "teaming"); // Calls Reports.save(plugin)
        Inventory gui = Bukkit.createInventory(player, 36, "§8Spieler melden");
        ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta itemmeta_axe = axe.getItemMeta();
        itemmeta_axe.setDisplayName("§eTeaming");
        itemmeta_axe.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ITEM_SPECIFICS});
        itemmeta_axe.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        ArrayList<String> itemlore_axe = new ArrayList();
        itemlore_axe.add(" §8► §fDer Spieler teamt mit");
        itemlore_axe.add("   §feiner anderen Rolle.");
        itemmeta_axe.setLore(itemlore_axe);
        axe.setItemMeta(itemmeta_axe);
        gui.setItem(4, this.mainbar_playerhead(player));
        gui.setItem(10, this.mainbar_hacking(false));
        gui.setItem(11, this.mainbar_offense(false));
        gui.setItem(12, this.mainbar_spam(false));
        gui.setItem(13, this.mainbar_advertise(false));
        gui.setItem(14, this.mainbar_obscenity(false));
        gui.setItem(15, this.mainbar_exploitation(false));
        gui.setItem(16, this.mainbar_teaming(true));
        gui.setItem(31, axe);
        return gui;
    }
}
