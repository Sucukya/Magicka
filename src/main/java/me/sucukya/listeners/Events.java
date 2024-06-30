package me.sucukya.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class Events implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu")) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onHandSwap(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if(e.getView().getType().equals(InventoryType.CHEST)) {
            if(e.getView().getTitle().equalsIgnoreCase("§aMenu")) {
                p.setItemOnCursor(null);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() !=null) {
            if(e.getView().getTitle().contains("Menu")) {
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                        e.setCancelled(true);
                        e.getWhoClicked().sendMessage("Clicked Slot: " + e.getSlot());
                    }
                }
            }
        }
    }

}
