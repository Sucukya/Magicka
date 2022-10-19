package me.sucukya.creator;

import de.tr7zw.nbtapi.NBTItem;
import me.sucukya.Magicka;
import me.sucukya.listeners.cheatMenu;
import me.sucukya.utility.Items;
import me.sucukya.utility.getPlayerStats;
import me.sucukya.utility.setPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class createMenu implements Listener {

    public static List<Player> namelist = new ArrayList<>();
    public static List<Player> manalist = new ArrayList<>();
    public static List<Player> cooldownlist = new ArrayList<>();
    public static List<Player> matList = new ArrayList<>();

    public static void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null,54,"§aItem Creation Menu");
        for(int i = 0; i < 54; i++) {
            inv.setItem(i,Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        HashMap<String,String> create = getPlayerStats.getCreate(p);
        List<String> lore = getPlayerStats.getList(p,"lore","create");

       if(create.get("material").equalsIgnoreCase("null")) {
           inv.setItem(10, Items.createItemStack(Material.STICK, 1, 0, "§aChoose the Material"));
       } else {
           inv.setItem(10, Items.createItemStackLore(Material.getMaterial(create.get("material").toUpperCase()), 1, 0, "§aChoose the Material","§a§l Chosen Material:§b " + create.get("material")));
       }
        if(create.get("name").equalsIgnoreCase("null")) {
            inv.setItem(12, Items.createItemStack(Material.NAME_TAG, 1, 0, "§aChoose the Name"));
        } else {
            inv.setItem(12, Items.createItemStackLore(Material.NAME_TAG, 1, 0, "§aChoose the Name","§a§l Chosen Name:§b " + create.get("name")));
        }
        if(create.get("manacost").equalsIgnoreCase("null")) {
            inv.setItem(14, Items.createItemStack(Material.BOOK, 1, 0, "§aChoose the Mana Cost"));
        } else {
            inv.setItem(14, Items.createItemStackLore(Material.BOOK, 1, 0, "§aChoose the Mana Cost" , "§a§l Chosen Mana Cost:§b " + create.get("manacost")+ " Mana"));
        }
        if(create.get("abilitycooldown").equalsIgnoreCase("null")) {
            inv.setItem(16, Items.createItemStack(Material.CLOCK, 1, 0, "§aChoose the Item Cooldown"));
        } else {
            inv.setItem(16, Items.createItemStackLore(Material.CLOCK, 1, 0, "§aChoose the Item Cooldown", "§a§l Chosen Item Cooldown:§b "  + create.get("abilitycooldown") +"§6s"));
        }
        inv.setItem(30,Items.createItemStackListLore(Material.BOOK,1,0,"§aEdit the Item's Lore!", lore));

        p.openInventory(inv);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if(e.getView().getTitle().equalsIgnoreCase("§aItem Creation Menu")) {
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aChoose the Material")) {
                        matList.add(p);
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§a§lType a §bMaterial §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aChoose the Name")) {
                        namelist.add(p);
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§a§lType a §bName §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aChoose the Mana Cost")) {
                        manalist.add(p);
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§a§lType a §bMana Cost §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aChoose the Item Cooldown")) {
                        cooldownlist.add(p);
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§a§lType an §bAbility Cooldown(in seconds) §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEdit the Item's Lore!")) {
                        e.setCancelled(true);
                        loreEditor.openOverview(p);
                    }
                }
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public void reopenMenu(Player p) {
        Bukkit.getScheduler().runTask(Magicka.plugin, () -> {
            createMenu.openMenu(p);
        });
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(matList.contains(p)) {
            String matstr = e.getMessage();
            if(Material.matchMaterial(matstr) != null) {
                setPlayerStats.setString(p, "material", matstr.toUpperCase(), "create");
                matList.remove(p);
                p.sendMessage("§a§lSet new Material to:§b " +  matstr.toUpperCase());
                e.setCancelled(true);
                reopenMenu(p);
            } else {
                p.sendMessage("§c§lThat is not a valid Material!");
                p.sendMessage("§c§lTry again!");
                e.setCancelled(true);
            }
        }
        if(namelist.contains(p)) {
            namelist.remove(p);
            String name = e.getMessage();
            setPlayerStats.setString(p,"name",name,"create");
            p.sendMessage("§a§lSet new Name to:§b " +  name);
            e.setCancelled(true);
            reopenMenu(p);
        }
        if(manalist.contains(p)) {
            String name = e.getMessage();
            boolean isnumber = isNumeric(name);
            if(isnumber) {
                setPlayerStats.setString(p, "manacost", name, "create");
                manalist.remove(p);
                p.sendMessage("§a§lSet new Mana Cost to:§b " +  name + " Mana");
                e.setCancelled(true);
                reopenMenu(p);
            } else {
                p.sendMessage("§c§lThat is not a number!");
                p.sendMessage("§c§lTry again!");
            }
        }
        if(cooldownlist.contains(p)) {
            String name = e.getMessage();
            boolean isnumber = isNumeric(name);
            if(isnumber) {
                setPlayerStats.setString(p, "abilitycooldown", name, "create");
                cooldownlist.remove(p);
                p.sendMessage("§a§lSet new Ability Cooldown to:§b " +  name + "§6s");
                e.setCancelled(true);
                reopenMenu(p);
            } else {
                p.sendMessage("§c§lThat is not a number!");
                p.sendMessage("§c§lTry again!");
            }
        }
    }

}
