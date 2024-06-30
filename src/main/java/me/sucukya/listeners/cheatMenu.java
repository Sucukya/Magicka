package me.sucukya.listeners;

import me.sucukya.commands.infinitemana;
import me.sucukya.creator.createMenu;
import me.sucukya.utility.Items;
import me.sucukya.utility.getPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class cheatMenu implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null) {
            if(e.getView().getTitle().equalsIgnoreCase("§aMenu")) {
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lCheat-Menu")) {
                        openMenu((Player) e.getWhoClicked());
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClickInMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if(e.getView().getTitle().equalsIgnoreCase("§cCheat-Menu")) {
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b§lInfinite Mana")) {
                        infinitemana.infinitemana(p);
                        openMenu(p);
                        e.setCancelled(true);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b§lCustom Item Creator")) {
                        createMenu.openMenu(p);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    public static void openMenu(Player p) {
        HashMap<String,Boolean> cheats = getPlayerStats.getCheats(p);
        Inventory inv = Bukkit.createInventory(null,54,"§cCheat-Menu");
        for(int i = 0; i < 54; i++) {
            inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        if(p.hasPermission("magicka.infinitemana")) {
            if(cheats.get("infinitemana")) {
               inv.setItem(10,Items.createItemStackLore(Material.LIME_DYE,1,0,"§b§lInfinite Mana", "§bCurrently turned §a§lON"));
            } else {
                inv.setItem(10,Items.createItemStackLore(Material.RED_DYE,1,0,"§b§lInfinite Mana", "§bCurrently turned §c§lOFF"));
            }

            inv.setItem(12, Items.createItemStackLore(Material.ANVIL,1,0,"§b§lCustom Item Creator","§bCreate your own §a§lcustom §bItems!"));
        }
        p.openInventory(inv);
    }

}
