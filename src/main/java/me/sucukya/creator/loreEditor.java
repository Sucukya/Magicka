package me.sucukya.creator;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.Magicka;
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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class loreEditor implements Listener {
    public static List<Player> loreListNew = new ArrayList<>();
    public static List<Player> editLore = new ArrayList<>();
    public static HashMap<Player,Integer> editingLine = new HashMap<>();

    public static void openOverview(Player p) {
        Inventory inv = Bukkit.createInventory(null,54,"§aLore Editor - Menu");
        List<String> lore = getPlayerStats.getList(p,"lore","create");

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

        for(int i = 0; i < lore.size();i++) {
            if(i <= 6) {
                inv.setItem(i + 10,Items.createItemStackLore(Material.PAPER,(i + 1),0,"§aLine " + (i + 1), lore.get(i)));
            }
            if(i > 6 && i<= 13) {
                inv.setItem(i + 12,Items.createItemStackLore(Material.PAPER,(i + 1),0,"§aLine " + (i + 1), lore.get(i)));
            }
            if(i > 13 && i <= 20) {
                inv.setItem(i + 14,Items.createItemStackLore(Material.PAPER,(i + 1),0,"§aLine " + (i + 1), lore.get(i)));
            }
            if(i > 20 && i <= 27) {
                inv.setItem(i + 16,Items.createItemStackLore(Material.PAPER,(i + 1),0,"§aLine " + (i + 1), lore.get(i)));
            }
        }
        inv.setItem(4,Items.createItemStackListLore(Material.BOOK,1,0,"§aFinal Lore",lore));
        if(lore.size() < 28) {
            inv.setItem(50,Items.createItemStackLore(Material.LIME_WOOL,1,0,"§aAdd a new Line to the Lore","§a Click to add a new Line"));
        } else if(lore.size() == 28) {
            inv.setItem(50,Items.createItemStackLore(Material.RED_WOOL,1,0,"§cCan't add a new Line to the Lore","§c You reached the maximum amount of lore!" ));
        }
        inv.setItem(48,Items.createItemStackLoreNBT(Material.ARROW,1,0,"§aGo back to last menu","§a <- Item Creator","lastMenu","creator"));
        inv.setItem(45,Items.createItemStackLore(Material.BARRIER,1,0,"§c§lDelete the entire Lore", "§c§lWARNING: This proccess is NOT reversable!"));
        p.openInventory(inv);
    }

    public static void openLineEditor(Player p, int line) {
        List<String> lore = getPlayerStats.getList(p,"lore","create");
        Inventory inv = Bukkit.createInventory(null,54,"§aEdit a Lore Line - Menu");

        for(int i = 0; i < 54;i++) {
            inv.setItem(i,Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        inv.setItem(13,Items.createItemStackLore(Material.PAPER,line,0,"§aLine " + line, lore.get(line - 1)));
        inv.setItem(30,Items.createItemStackLoreNBT(Material.WRITABLE_BOOK,1,0,"§aEdit Line", "§a Click to edit this Line","line",String.valueOf(line)));
        inv.setItem(32,Items.createItemStackLoreNBT(Material.BARRIER,1,0,"§cDelete Line", "§c Click to delete this Line","line", String.valueOf(line)));
        inv.setItem(49,Items.createItemStackLore(Material.ARROW,1,0,"§aGo back to last menu", "§a <- Lore Overview"));
        p.openInventory(inv);
    }

    public static void openConfirm(Player p) {
        Inventory inv = Bukkit.createInventory(null,27,"§c§lReally delete entire Lore? - Menu");
        for(int i = 0; i < 27; i++) {
            inv.setItem(i,Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        inv.setItem(11,Items.createItemStackLore(Material.LIME_CONCRETE,1,0,"§a§lConfirm","§c§l This will delete the Lore"));
        inv.setItem(15,Items.createItemStackLore(Material.RED_CONCRETE,1,0,"§c§lCancel","§a§l This will keep the Lore"));
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if(e.getView().getTitle().equalsIgnoreCase("§aEdit a Lore Line - Menu")) {
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEdit Line")) {
                        editLore.add(p);
                        NBTItem nbtitem = new NBTItem(e.getCurrentItem());
                        int line = Integer.parseInt(nbtitem.getString("line")) - 1;
                        editingLine.put(p,line);
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§aType your edit Line of Lore in Chat!");
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDelete Line")) {
                        List<String> lore = getPlayerStats.getList(p,"lore","create");
                        List<String> newlore = new ArrayList<>();
                        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                        NBTItem nbtitem = new NBTItem(e.getCurrentItem());
                        int exclude = Integer.parseInt(nbtitem.getString("line")) - 1;
                        NBTCompound nbticreate = nbti.getCompound("create");
                        NBTCompound nbtilore = nbticreate.getCompound("lore");
                        for(int i = 0; i < lore.size(); i++) {
                            nbtilore.removeKey(String.valueOf(i));
                        }
                            for (int i = 0; i < lore.size(); i++) {
                                if (i == exclude) {
                                } else {
                                    newlore.add(lore.get(i));
                                }
                        }
                        ItemStack menu = nbti.getItem();
                        p.getInventory().setItem(8,menu);
                        p.sendMessage(newlore.toString());
                        p.sendMessage(String.valueOf(lore.size()));
                        setPlayerStats.setList(p,"lore",newlore,"create");
                        e.setCancelled(true);
                        openOverview(p);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                        e.setCancelled(true);
                        openOverview(p);
                    }
                }
            }
            if(e.getView().getTitle().equalsIgnoreCase("§aLore Editor - Menu")) {
                if(e.getCurrentItem() !=null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAdd a new Line to the Lore")) {
                        loreListNew.add(p);
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§a§lType a new §bLore Line §a§lin your chat!");
                    }
                    if(e.getCurrentItem().getType() == Material.PAPER) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Line")) {
                            openLineEditor(p, e.getCurrentItem().getAmount());
                        }
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCan't add a new Line to the Lore")) {
                        p.sendMessage("§c You have reached the maximum amount of lore!");
                        e.setCancelled(true);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                        e.setCancelled(true);
                        createMenu.openMenu(p);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lDelete the entire Lore")) {
                        e.setCancelled(true);
                        openConfirm(p);
                    }
                }
            }
            if(e.getView().getTitle().equalsIgnoreCase("§c§lReally delete entire Lore? - Menu")) {
                if(e.getCurrentItem() !=null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lConfirm")) {
                        List<String> lore = getPlayerStats.getList(p,"lore","create");
                        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                        NBTCompound nbticreate = nbti.getCompound("create");
                        NBTCompound nbtilore = nbticreate.getCompound("lore");
                        for(int i = 0; i < lore.size(); i++) {
                            nbtilore.removeKey(String.valueOf(i));
                        }
                        ItemStack menu = nbti.getItem();
                        p.getInventory().setItem(8,menu);
                        e.setCancelled(true);
                        openOverview(p);

                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lCancel")) {
                        openOverview(p);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    public void reopenOverviewMenu(Player p) {
        Bukkit.getScheduler().runTask(Magicka.plugin, () -> {
            openOverview(p);
        });
    }
    public void reopenLoreEditor(Player p, Integer line) {
        Bukkit.getScheduler().runTask(Magicka.plugin, () -> {
            openLineEditor(p, line);
        });
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        List<String> lore = getPlayerStats.getList(p,"lore","create");
        if(loreListNew.contains(p)) {
            loreListNew.remove(p);
            String loreLine = e.getMessage().replace("&","§");
            if(loreLine.equalsIgnoreCase("cancel")) {
                e.setCancelled(true);
                reopenOverviewMenu(p);
            } else {
                lore.add(loreLine);
                setPlayerStats.setList(p, "lore", lore, "create");
                e.setCancelled(true);
                reopenOverviewMenu(p);
            }
        }

        if(editLore.contains(p)) {
            List<String> newlore = new ArrayList<>();
            String newline = e.getMessage().replace("&", "§");
            NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
            int line = editingLine.get(p);
            if (newline.equalsIgnoreCase("cancel")) {
                editLore.remove(p);
                e.setCancelled(true);
                reopenLoreEditor(p, (line + 1));
            } else {
                NBTCompound nbticreate = nbti.getCompound("create");
                NBTCompound nbtilore = nbticreate.getCompound("lore");
                for (int i = 0; i < lore.size(); i++) {
                    nbtilore.removeKey(String.valueOf(i));
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (i == line) {
                        newlore.add(newline);
                    } else {
                        newlore.add(lore.get(i));
                    }
                }
                ItemStack menu = nbti.getItem();
                p.getInventory().setItem(8, menu);
                setPlayerStats.setList(p, "lore", newlore, "create");
                e.setCancelled(true);
                editLore.remove(p);
                editingLine.remove(p);
                reopenLoreEditor(p, line + 1);
            }
        }
    }
}
