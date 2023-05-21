package me.sucukya.listeners;

import me.sucukya.utility.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class mainMenu implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) {
        } else {
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
            } else {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu")) {
                        e.setCancelled(true);
                        p.getInventory().getItem(8).setAmount(1);
                        openMenu(p);
                    for(int i = 0; i < 40 ; i++) {
                        if(i != 8) {
                            if(p.getInventory().getItem(i) != null && !(p.getInventory().getItem(i).getType().equals(Material.AIR))) {
                                if(p.getInventory().getItem(8).getType().equals(Material.NETHER_STAR)) {
                                    if (p.getInventory().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu")) {
                                        p.getInventory().setItem(i, new ItemStack(Material.AIR));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.getOpenInventory().getType() != InventoryType.CHEST) {
                    if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
                    } else {
                        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu")) {
                            openMenu(p);
                        }
                    }
                } else {
                    e.setCancelled(true);
                }
            }
    }

    public void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null,54,"§aMenu");
        for(int i = 0; i < 54; i++) {
            inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE, 1 , 0, " "));
        }
        if(p.hasPermission("magicka.cheatmenu")) {
            inv.setItem(10, Items.createItemStack(Material.COMMAND_BLOCK,1,0,"§c§lCheat-Menu"));
        }
        p.openInventory(inv);
    }

}
