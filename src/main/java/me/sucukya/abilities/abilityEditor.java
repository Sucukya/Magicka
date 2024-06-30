package me.sucukya.abilities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
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
import java.util.List;

public class abilityEditor implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if(e.getView().getTitle().equalsIgnoreCase("§aItem Creation Menu")) {
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEdit the Item's Ability!")) {
                        e.setCancelled(true);
                        openAbilityEditor(p);
                    }
                }
            }
        }
    }

    public static void openAbilityEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null,54,"§aEdit the Item's Ability - Menu");
        for(int i = 0; i < 54; i++) {
            inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        HashMap<String,String> ability = getPlayerStats.getMap(p,"ability","create");
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound create = nbti.getCompound("create");
        NBTCompound abilityComp = create.getCompound("ability");
        NBTCompound nbtCompound = getPlayerStats.getAbilityFunctionsComp(p);

        if(ability.get("name").equalsIgnoreCase("null")) {
            inv.setItem(10, Items.createItemStack(Material.NAME_TAG, 1, 0, "§aChoose the Ability Name"));
        } else {
            inv.setItem(10, Items.createItemStackLore(Material.NAME_TAG, 1, 0, "§aChoose the Ability Name" , "§a§l Chosen Ability Name:§b " + ability.get("name")));
        }
        if(nbtCompound.getKeys().size() == 0) {
            inv.setItem(12, Items.createItemStack(Material.ENDER_EYE, 1, 0, "§aEdit the Ability Function"));
        } else {
            inv.setItem(12, Items.createItemStackLore(Material.ENDER_EYE, 1, 0, "§aEdit the Ability Function" , "§a§l Chosen Ability Function:§b " ));
        }
        if(ability.get("manacost").equalsIgnoreCase("null")) {
            inv.setItem(14, Items.createItemStack(Material.BOOK, 1, 0, "§aChoose the Mana Cost"));
        } else {
            inv.setItem(14, Items.createItemStackLore(Material.BOOK, 1, 0, "§aChoose the Mana Cost" , "§a§l Chosen Mana Cost:§b " + abilityComp.getDouble("manacost")+ " Mana"));
        }
        if(ability.get("cooldown").equalsIgnoreCase("null")) {
            inv.setItem(16, Items.createItemStack(Material.CLOCK, 1, 0, "§aChoose the Item Cooldown"));
        } else {
            inv.setItem(16, Items.createItemStackLore(Material.CLOCK, 1, 0, "§aChoose the Item Cooldown", "§a§l Chosen Item Cooldown:§b "  + abilityComp.getDouble("cooldown") +"§6s"));
        }
        inv.setItem(49,Items.createItemStackLoreNBT(Material.ARROW,1,0,"§aGo back to last menu","§a <- Item Creator","lastMenu","creator"));
        p.openInventory(inv);
    }

}
