package me.sucukya.abilities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import me.sucukya.utility.Items;
import me.sucukya.utility.getPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class editAction implements Listener {
    public static void openMenu(Player p, String action) {
        NBTCompound abilityFunctions = getPlayerStats.getAbilityFunctionsComp(p);
        NBTCompound abilityFunction = abilityFunctions.getCompound(action);
        Inventory inv = Bukkit.createInventory(null,45,"§aEditing Action " + action + " - Menu");
        for(int i = 0; i < 45; i++) {
            inv.setItem(i, Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        switch(abilityFunction.getString("type")) {
            case "teleport":
                inv.setItem(21,Items.createItemStackLore(Material.ENDER_PEARL,1,0,"§aCurrent Type of Function","§a§l Teleport"));
                inv.setItem(23,Items.createItemStackLore(Material.BOW,1,0,"§aCurrent Range of Function","§a§l " + abilityFunction.getDouble("range") + " blocks"));
                break;
            case "damage":
                inv.setItem(19,Items.createItemStackLore(Material.DIAMOND_SWORD,1,0,"§aCurrent Type of Function","§a§l Damage"));
                inv.setItem(21,Items.createItemStackLore(Material.BOW,1,0,"§aCurrent Range of Function","§a§l " + abilityFunction.getDouble("range") + " blocks"));
                inv.setItem(23,Items.createItemStackLore(Material.WRITABLE_BOOK,1,0,"§cCurrent Damage Type of Function","§c§l " + abilityFunction.getString("damageType")));
                inv.setItem(25,Items.createItemStackLore(Material.NETHERITE_SWORD,1,0,"§cCurrent Damage Amount of Function","§c§l " + abilityFunction.getDouble("damage")));
                break;
            case "applyEffect":
                inv.setItem(19,Items.createItemStackLore(Material.POTION,1,0,"§aCurrent Type of Function","§a§l Potion Effect"));
                if(abilityFunction.getBoolean("selfApply")) {
                    inv.setItem(13,Items.createItemStackLore(Material.LIME_CONCRETE,1,0,"§aCurrent Target of Effect","§a§l Yourself"));
                } else {
                    inv.setItem(13,Items.createItemStackLore(Material.RED_CONCRETE,1,0,"§aCurrent Target of Effect","§c§l Other Entities"));
                }
                if(!abilityFunction.getBoolean("selfApply")) {
                    inv.setItem(21, Items.createItemStackLore(Material.BOW, 1, 0, "§aCurrent Range of Function", "§a§l " + abilityFunction.getDouble("range") + " blocks"));
                }

                inv.setItem(22,Items.createItemStackLore(Material.POTION,1,0,"§cCurrent Potion Effect of Function","§c§l " + abilityFunction.getString("potion")));
                inv.setItem(23,Items.createItemStackLore(Material.EXPERIENCE_BOTTLE,1,0,"§aCurrent Level of Potion of Function","§a§l " + abilityFunction.getDouble("level")));
                inv.setItem(25,Items.createItemStackLore(Material.CLOCK,1,0,"§aCurrent Duration of Potion of Function","§a§l " + abilityFunction.getDouble("duration")));
                break;
            case "changeStat":
                inv.setItem(20,Items.createItemStackLore(Material.WRITABLE_BOOK,1,0,"§aCurrent Type of Function","§a§l Stat Change"));
                inv.setItem(22,Items.createItemStackLore(Material.NETHER_STAR,1,0,"§aCurrent Stat that is changed"," §b§l" + abilityFunction.getString("stat")));
                if(abilityFunction.getDouble("amount") < 0) {
                    inv.setItem(24,Items.createItemStackLore(Material.POISONOUS_POTATO,1,0,"§aCurrent Amount the Stat is changed by","§c§l " + abilityFunction.getDouble("amount")));
                } else {
                    inv.setItem(24,Items.createItemStackLore(Material.GOLDEN_APPLE,1,0,"§aCurrent Amount the Stat is changed by","§a§l +" + abilityFunction.getDouble("amount")));
                }
                break;
        }

        inv.setItem(39,Items.createItemStackLoreNBT(Material.ARROW,1,0,"§aGo back to last menu","§a <- Function Overview","lastMenu","funcoverview"));
        inv.setItem(41,Items.createItemStackLore(Material.BARRIER,1,0,"§cDelete Action", "§c Click to delete this Action"));

        p.openInventory(inv);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getView().getTitle().contains("§aEditing Action")) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    String action = e.getView().getTitle().replace("§aEditing Action ","").replace(" - Menu","");
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                        e.setCancelled(true);
                        abilityCreator.openMenu(p);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDelete Action")) {
                        e.setCancelled(true);
                        openConfirm(p,action);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aCurrent Type of Function")) {
                        e.setCancelled(true);
                        editValuesMenus.editType(p,action);
                    }
                }
            }
            if(e.getView().getTitle().contains("§c§lReally delete Action")) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lConfirm")) {
                        String action = e.getView().getTitle().replace("§c§lReally delete Action ","").replace("? - Menu","");
                        functionEditing.deleteFunction(p,action);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lCancel")) {
                        e.setCancelled(true);
                        String action = e.getView().getTitle().replace("§c§lReally delete Action ","").replace("? - Menu","");
                        openMenu(p,action);
                    }
                }
            }
        }
    }
    public static void openConfirm(Player p, String action) {
        Inventory inv = Bukkit.createInventory(null,27,"§c§lReally delete Action " + action + "? - Menu");
        for(int i = 0; i < 27; i++) {
            inv.setItem(i,Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        inv.setItem(11,Items.createItemStackLore(Material.LIME_CONCRETE,1,0,"§a§lConfirm","§c§l This will delete the Action"));
        inv.setItem(15,Items.createItemStackLore(Material.RED_CONCRETE,1,0,"§c§lCancel","§a§l This will keep the Action"));
        p.openInventory(inv);
    }
}
