package me.sucukya.abilities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.utility.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class editValuesMenus implements Listener {

    public static List<Player> rangeList = new ArrayList<>();
    public static List<Player> damageList = new ArrayList<>();
    public static List<Player> statList = new ArrayList<>();
    public static List<Player> levelList = new ArrayList<>();
    public static List<Player> durationList = new ArrayList<>();
    public static HashMap<Player,String> actionMap = new HashMap<>();

    public static void editType(Player p, String action) {
        Inventory inv = Bukkit.createInventory(null,45,"§aEditing the Type of Function " + action + " - Menu");
        for(int i = 0; i < 45; i++) {
            inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        inv.setItem(19, Items.createItemStack(Material.ENDER_PEARL,1,0,"§aChange Type to §b§lteleport"));
        inv.setItem(21, Items.createItemStack(Material.DIAMOND_SWORD,1,0,"§aChange Type to §b§ldamage"));
        inv.setItem(23, Items.createItemStack(Material.POTION,1,0,"§aChange Type to §b§lapplyEffect"));
        inv.setItem(25, Items.createItemStack(Material.WRITABLE_BOOK,1,0,"§aChange Type to §b§lchangeStat"));

        inv.setItem(40,Items.createItemStackLoreNBT(Material.ARROW,1,0,"§aGo back to last menu","§a <- Action " + action + " Overview","lastMenu","actionoverview"));
        p.openInventory(inv);
    }
    public static void editStat(Player p,String action) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound stats = nbti.getCompound("stats");

        Inventory inv = Bukkit.createInventory(null, 54, "§aEditing the changed Stat of Function " + action + " - Menu");
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE, 1, 0, " "));
        }
        for (int i = 0; i < 28; i++) {
            if (i <= 6) {
                inv.setItem(i + 10, new ItemStack(Material.AIR));
            }
            if (i > 6 && i <= 13) {
                inv.setItem(i + 12, new ItemStack(Material.AIR));
            }
            if (i > 13 && i <= 20) {
                inv.setItem(i + 14, new ItemStack(Material.AIR));
            }
            if (i > 20 && i <= 27) {
                inv.setItem(i + 16, new ItemStack(Material.AIR));
            }
        }
        List<String> statsList = stats.getKeys().stream().toList();
        for(int slot =0; slot < stats.getKeys().size(); slot++) {
            String stat = statsList.get(slot);
            Material mat = Material.PAPER;
            if (slot <= 6) {
                inv.setItem(slot + 10, Items.createItemStackLore(mat, 1, 0, "§aChange to:", "§b" + stat));
            }
            if (slot > 6 && slot <= 13) {
                inv.setItem(slot + 12, Items.createItemStackLore(mat, 1, 0, "§aChange to:", "§b" + stat));
            }
            if (slot > 13 && slot <= 20) {
                inv.setItem(slot + 14, Items.createItemStackLore(mat, 1, 0, "§aChange to:", "§b" + stat));
            }
            if (slot > 20 && slot <= 27) {
                inv.setItem(slot + 16, Items.createItemStackLore(mat, 1, 0, "§aChange to:", "§b" + stat));
            }
        }
        inv.setItem(49,Items.createItemStackLoreNBT(Material.ARROW,1,0,"§aGo back to last menu","§a <- Action " + action + " Overview","lastMenu","actionoverview"));
        p.openInventory(inv);
    }

    public static void editPotion(Player p,String action) {

    }

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getView().getTitle().contains("§aEditing the Type of Function")) {
                String action = e.getView().getTitle().replace("§aEditing the Type of Function ","").replace(" - Menu","");
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§aChange Type to")) {
                        String type = e.getCurrentItem().getItemMeta().getDisplayName().replace("§aChange Type to §b§l", "");
                        editValues.changeType(p,action,type);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                        editAction.openMenu(p,action);
                    }
                }
                }
            if (e.getView().getTitle().contains("§aEditing Action")) {
                String action = e.getView().getTitle().replace("§aEditing Action ","").replace(" - Menu","");
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aCurrent Range of Function")) {
                        e.setCancelled(true);
                        rangeList.add(p);
                        actionMap.put(p,action);
                        p.closeInventory();
                        p.sendMessage("§a§lType the §bRange §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCurrent Damage Amount of Function")) {
                        e.setCancelled(true);
                        damageList.add(p);
                        actionMap.put(p,action);
                        p.closeInventory();
                        p.sendMessage("§a§lType the §cDamage §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aCurrent Amount the Stat is changed by")) {
                        e.setCancelled(true);
                        statList.add(p);
                        actionMap.put(p,action);
                        p.closeInventory();
                        p.sendMessage("§a§lType the §bAmount §a§lthe stat is changed by §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aCurrent Level of Potion of Function")) {
                        e.setCancelled(true);
                        levelList.add(p);
                        actionMap.put(p,action);
                        p.closeInventory();
                        p.sendMessage("§a§lType the §bLevel §a§lof the potion §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aCurrent Duration of Potion of Function")) {
                        e.setCancelled(true);
                        durationList.add(p);
                        actionMap.put(p,action);
                        p.closeInventory();
                        p.sendMessage("§a§lType the §bDuration §a§lof the potion in your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aCurrent Stat that is changed")) {
                        e.setCancelled(true);
                        editStat(p,action);
                    }
                }
            }
            if (e.getView().getTitle().contains("§aEditing the changed Stat of Function")) {
                String action = e.getView().getTitle().replace("§aEditing the changed Stat of Function ","").replace(" - Menu","");
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aChange to:")) {
                        e.setCancelled(true);
                        String stats = e.getCurrentItem().getItemMeta().getLore().get(0);
                        String stat = stats.replace("§b", "").toLowerCase();
                        editValues.changeStat(p, action, stat);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                        e.setCancelled(true);
                        editAction.openMenu(p,action);
                    }
                }
            }
        }
    }
}
