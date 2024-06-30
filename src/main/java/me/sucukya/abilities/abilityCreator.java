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
import java.util.List;



public class abilityCreator implements Listener {
    public static void openMenu(Player p) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtCreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtCreate.getCompound("abilityFunction");

        Inventory inv = Bukkit.createInventory(null,54,"§aAbility Functionality Editing Menu");
            for(int i = 0; i < 54;i++) {
                inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
            }

            for(int i = 0; i < 28;i++) {
                if(i <= 6) {
                    inv.setItem(i + 10, new ItemStack(Material.AIR));
                }
                if(i > 6 && i<= 13) {
                    inv.setItem(i + 12, new ItemStack(Material.AIR));
                }
                if(i > 13 && i <= 20) {
                    inv.setItem(i + 14, new ItemStack(Material.AIR));
                }
                if(i > 20 && i <= 27) {
                    inv.setItem(i + 16, new ItemStack(Material.AIR));
                }
            }

            for(int i = 0; i < abilityFunctions.getKeys().size(); i++) {
                NBTCompound abilityFunction = abilityFunctions.getCompound(i + 1 + "");
                String action = String.valueOf(i + 1);
                Material mat = Material.PAPER;
                switch (abilityFunction.getString("type")) {
                    case "teleport":
                        mat = Material.ENDER_PEARL;
                        break;
                    case "damage":
                        mat = Material.DIAMOND_SWORD;
                        break;
                    case "applyEffect":
                        mat = Material.POTION;
                        break;
                    case "changeStat":
                        mat = Material.WRITABLE_BOOK;
                        break;
                }
                if(i <= 6) {
                    inv.setItem(i + 10,Items.createItemStackListLore(mat,(i + 1),0,"§aAction " + (i + 1), loreCreator(p,action)));
                }
                if(i > 6 && i<= 13) {
                    inv.setItem(i + 12,Items.createItemStackListLore(mat,(i + 1),0,"§aAction " + (i + 1), loreCreator(p,action)));
                }
                if(i > 13 && i <= 20) {
                    inv.setItem(i + 14,Items.createItemStackListLore(mat,(i + 1),0,"§aAction " + (i + 1), loreCreator(p,action)));
                }
                if(i > 20 && i <= 27) {
                    inv.setItem(i + 16,Items.createItemStackListLore(mat,(i + 1),0,"§aAction " + (i + 1), loreCreator(p,action)));
                }

            }
        if(abilityFunctions.getKeys().size() < 28) {
            inv.setItem(50,Items.createItemStackLore(Material.LIME_WOOL,1,0,"§aAdd a new Function to the Ability","§a Click to add a new Function"));
        } else if(abilityFunctions.getKeys().size() == 28) {
            inv.setItem(50,Items.createItemStackLore(Material.RED_WOOL,1,0,"§cCan't add a new Function to the Ability","§c You reached the maximum amount of functions!" ));
        }
        inv.setItem(48,Items.createItemStackLoreNBT(Material.ARROW,1,0,"§aGo back to last menu","§a <- Ability Menu","lastMenu","ability"));
            p.openInventory(inv);
    }
    public static List<String> loreCreator(Player p,String action) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtCreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtCreate.getCompound("abilityFunction");
        NBTCompound abilityFunction = abilityFunctions.getCompound(action);

        List<String> lore = new ArrayList<>();

        switch (abilityFunction.getString("type")) {
            case "teleport":
                lore.add(" §aType: §lTeleport");
                lore.add(" §aRange: §l" + abilityFunction.getDouble("range") + " blocks");
                break;
            case "damage":
                lore.add(" §aType: §lDamage");
                lore.add(" §aRange: §l" + abilityFunction.getDouble("range") + " blocks");
                lore.add(" §cDamage Type: §l" + abilityFunction.getString("damageType"));
                lore.add(" §cDamage: §l" + abilityFunction.getDouble("damage"));
                break;
            case "applyEffect":
                lore.add(" §aType: §lApply Effect");
                if(!abilityFunction.getBoolean("selfApply")) {
                    lore.add(" §aRange: §l" + abilityFunction.getDouble("range") + " blocks");
                }
                lore.add(" §cPotion: " + abilityFunction.getString("potion"));
                lore.add(" §aLevel: " + abilityFunction.getDouble("level"));
                lore.add(" §aDuration: " + abilityFunction.getDouble("duration"));
                break;
            case "changeStat":
                lore.add(" §aType: §lStat Change");
                lore.add(" §aChanged Stat: §b§l" + abilityFunction.getString("stat"));
                if(abilityFunction.getDouble("amount") < 0) {
                    lore.add(" §aAmount Changed: §c§l " + abilityFunction.getDouble("amount"));
                } else {
                    lore.add(" §aAmount Changed: §l+ " + abilityFunction.getDouble("amount"));
                }
                break;
        }

        return lore;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getView().getTitle().equalsIgnoreCase("§aAbility Functionality Editing Menu")) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                        e.setCancelled(true);
                        abilityEditor.openAbilityEditor(p);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§aAction")) {
                        String action = e.getCurrentItem().getItemMeta().getDisplayName().replace("§aAction ","");
                        p.sendMessage(action);
                        editAction.openMenu(p,action);
                        e.setCancelled(true);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAdd a new Function to the Ability")) {
                        functionEditing.addFunction(p);
                        e.setCancelled(true);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCan't add a new Function to the Ability")) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}