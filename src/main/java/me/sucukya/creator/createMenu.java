package me.sucukya.creator;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.Magicka;
import me.sucukya.abilities.abilityCreator;
import me.sucukya.abilities.abilityEditor;
import me.sucukya.abilities.editAction;
import me.sucukya.abilities.editValuesMenus;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class createMenu implements Listener {

    public static List<Player> namelist = new ArrayList<>();
    public static List<Player> abilityNamelist = new ArrayList<>();
    public static List<Player> manalist = new ArrayList<>();
    public static List<Player> cooldownlist = new ArrayList<>();
    public static List<Player> matList = new ArrayList<>();

    public static void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null,45,"§aItem Creation Menu");
        for(int i = 0; i < 45; i++) {
            inv.setItem(i,Items.createItemStack(Material.BLACK_STAINED_GLASS_PANE,1,0," "));
        }
        HashMap<String,String> create = getPlayerStats.getCreate(p);
        List<String> lore = getPlayerStats.getList(p,"lore","create");

       if(create.get("material").equalsIgnoreCase("null")) {
           inv.setItem(28, Items.createItemStack(Material.STICK, 1, 0, "§aChoose the Material"));
       } else {
           inv.setItem(28, Items.createItemStackLore(Material.getMaterial(create.get("material").toUpperCase()), 1, 0, "§aChoose the Material","§a§l Chosen Material:§b " + create.get("material").replace("_"," ") ));
       }
        if(create.get("name").equalsIgnoreCase("null")) {
            inv.setItem(30, Items.createItemStack(Material.NAME_TAG, 1, 0, "§aChoose the Name"));
        } else {
            inv.setItem(30, Items.createItemStackLore(Material.NAME_TAG, 1, 0, "§aChoose the Name","§a§l Chosen Name:§b " + create.get("name")));
        }
        if(create.get("sound").equalsIgnoreCase("null")) {
            inv.setItem(31, Items.createItemStack(Material.JUKEBOX, 1,0,"§aChoose the Sound"));
        } else {
            inv.setItem(31, Items.createItemStackLore(Material.JUKEBOX,1,0,"§aChoose the Sound", "§a§l Chosen Sound:§b " + create.get("sound")));
        }
        inv.setItem(32,Items.createItemStackListLore(Material.WRITABLE_BOOK,1,0,"§aEdit the Item's Lore!", lore));
        inv.setItem(34,Items.createItemStack(Material.ENCHANTED_BOOK,1,0,"§aEdit the Item's Ability!"));
        inv.setItem(13,constructItem(p));
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
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEdit the Item's Lore!")) {
                        e.setCancelled(true);
                        loreEditor.openOverview(p);
                    }
                    if(e.getSlot() == 13) {
                        e.setCancelled(true);
                        p.getInventory().addItem(e.getCurrentItem());
                    }
                }
            }
            if(e.getView().getTitle().equalsIgnoreCase("§aEdit the Item's Ability - Menu")) {
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
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aChoose the Ability Name")) {
                    abilityNamelist.add(p);
                    e.setCancelled(true);
                    p.closeInventory();
                    p.sendMessage("§a§lType a §bAbility Name §a§lin your chat!");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEdit the Ability Function")) {
                    e.setCancelled(true);
                    abilityCreator.openMenu(p);
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo back to last menu")) {
                    e.setCancelled(true);
                    openMenu(p);
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
    public void reopenAbilityMenu(Player p) {
        Bukkit.getScheduler().runTask(Magicka.plugin, () -> {
            abilityEditor.openAbilityEditor(p);
        });
    }
    public void reopenActionMenu(Player p,String action) {
        Bukkit.getScheduler().runTask(Magicka.plugin, () -> {
            editAction.openMenu(p,action);
        });
    }
    public static ItemStack constructItem(Player p) {
        NBTItem nbtiplayer = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtcreate = nbtiplayer.getCompound("create");
        ItemStack stack = new ItemStack(Material.STICK);
        if(nbtcreate.getString("material").equalsIgnoreCase("null")) {
            stack = new ItemStack(Material.STICK);
        } else {
            stack = new ItemStack(Material.getMaterial(nbtcreate.getString("material").toUpperCase()));
        }
        NBTItem nbti = new NBTItem(stack);
        NBTCompound nbtStats = nbti.addCompound("stats");
        NBTCompound ability = nbtStats.addCompound("ability");
        NBTCompound abilityFunction = nbtStats.addCompound("abilityFunction");
        NBTCompound lore = nbtStats.addCompound("lore");
        ability.mergeCompound(nbtcreate.getCompound("ability"));
        abilityFunction.mergeCompound(nbtcreate.getCompound("abilityFunction"));
        lore.mergeCompound(nbtcreate.getCompound("lore"));
        nbtStats.setString("name",nbtcreate.getString("name"));
        nbtStats.setString("material",nbtcreate.getString("material"));
        stack = nbti.getItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName(nbtcreate.getString("name"));
        meta.setLore(getPlayerStats.getList(p,"lore","create"));
        stack.setItemMeta(meta);



        return stack;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(matList.contains(p)) {
            String matstr = e.getMessage();
            if(matstr.equalsIgnoreCase("cancel")) {
                matList.remove(p);
                e.setCancelled(true);
                reopenMenu(p);
            } else {
                if (Material.matchMaterial(matstr) != null) {
                    setPlayerStats.setString(p, "material", matstr.toUpperCase(), "create");
                    matList.remove(p);
                    p.sendMessage("§a§lSet new Material to:§b " + matstr.toUpperCase());
                    e.setCancelled(true);
                    reopenMenu(p);
                } else {
                    p.sendMessage("§c§lThat is not a valid Material!");
                    p.sendMessage("§c§lTry again!");
                    e.setCancelled(true);
                }
            }
        }
        if(namelist.contains(p)) {
            namelist.remove(p);

            String name = e.getMessage().replace("&","§");
            if(name.equalsIgnoreCase("cancel")) {
                namelist.remove(p);
                e.setCancelled(true);
                reopenMenu(p);
            } else {
                setPlayerStats.setString(p, "name", name, "create");
                p.sendMessage("§a§lSet new Name to:§b " + name);
                e.setCancelled(true);
                reopenMenu(p);
            }
        }
        if(abilityNamelist.contains(p)) {
            abilityNamelist.remove(p);

            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                e.setCancelled(true);
                reopenAbilityMenu(p);
            } else {
                setPlayerStats.setStringBelow(p, "create", "ability", "name",name);
                p.sendMessage("§a§lSet new Name to:§b " + name);
                e.setCancelled(true);
                reopenAbilityMenu(p);
            }
        }
        if(manalist.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                manalist.remove(p);
                e.setCancelled(true);
                reopenAbilityMenu(p);
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    setPlayerStats.setDoubleBelow(p,"create","ability","manacost", Double.valueOf(name));
                    manalist.remove(p);
                    p.sendMessage("§a§lSet new Mana Cost to:§b " + name + " Mana");
                    e.setCancelled(true);
                    reopenAbilityMenu(p);
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
        if(editValuesMenus.rangeList.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                editValuesMenus.rangeList.remove(p);
                e.setCancelled(true);
                reopenActionMenu(p,editValuesMenus.actionMap.get(p));
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtcreate = nbti.getCompound("create");
                    NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
                    NBTCompound abilityFunction = abilityFunctions.getCompound(editValuesMenus.actionMap.get(p));
                    abilityFunction.setDouble("range", Double.valueOf(name));
                    p.getInventory().setItem(8,nbti.getItem());
                    editValuesMenus.rangeList.remove(p);
                    p.sendMessage("§a§lSet new §bRange §ato:§b " + name + " blocks");
                    e.setCancelled(true);
                    reopenActionMenu(p,editValuesMenus.actionMap.get(p));
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
        if(editValuesMenus.damageList.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                editValuesMenus.damageList.remove(p);
                e.setCancelled(true);
                reopenActionMenu(p,editValuesMenus.actionMap.get(p));
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtcreate = nbti.getCompound("create");
                    NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
                    NBTCompound abilityFunction = abilityFunctions.getCompound(editValuesMenus.actionMap.get(p));
                    abilityFunction.setDouble("damage", Double.valueOf(name));
                    p.getInventory().setItem(8,nbti.getItem());
                    editValuesMenus.damageList.remove(p);
                    p.sendMessage("§a§lSet new §cDamage §ato:§c " + name);
                    e.setCancelled(true);
                    reopenActionMenu(p,editValuesMenus.actionMap.get(p));
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
        if(editValuesMenus.statList.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                editValuesMenus.statList.remove(p);
                e.setCancelled(true);
                reopenActionMenu(p,editValuesMenus.actionMap.get(p));
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtcreate = nbti.getCompound("create");
                    NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
                    NBTCompound abilityFunction = abilityFunctions.getCompound(editValuesMenus.actionMap.get(p));
                    abilityFunction.setDouble("amount", Double.valueOf(name));
                    p.getInventory().setItem(8,nbti.getItem());
                    editValuesMenus.statList.remove(p);
                    p.sendMessage("§a§lSet new §bAmount §athe stat is changed by to:§b " + name);
                    e.setCancelled(true);
                    reopenActionMenu(p,editValuesMenus.actionMap.get(p));
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
        if(editValuesMenus.levelList.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                editValuesMenus.levelList.remove(p);
                e.setCancelled(true);
                reopenActionMenu(p,editValuesMenus.actionMap.get(p));
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtcreate = nbti.getCompound("create");
                    NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
                    NBTCompound abilityFunction = abilityFunctions.getCompound(editValuesMenus.actionMap.get(p));
                    abilityFunction.setDouble("level", Double.valueOf(name));
                    p.getInventory().setItem(8,nbti.getItem());
                    editValuesMenus.levelList.remove(p);
                    p.sendMessage("§a§lSet new §bLevel §a§lof the potion to:§b " + name);
                    e.setCancelled(true);
                    reopenActionMenu(p,editValuesMenus.actionMap.get(p));
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
        if(editValuesMenus.durationList.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                editValuesMenus.durationList.remove(p);
                e.setCancelled(true);
                reopenActionMenu(p,editValuesMenus.actionMap.get(p));
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtcreate = nbti.getCompound("create");
                    NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
                    NBTCompound abilityFunction = abilityFunctions.getCompound(editValuesMenus.actionMap.get(p));
                    abilityFunction.setDouble("duration", Double.valueOf(name));
                    p.getInventory().setItem(8,nbti.getItem());
                    editValuesMenus.durationList.remove(p);
                    p.sendMessage("§a§lSet new §bDuration §a§lof the potion to:§b " + name);
                    e.setCancelled(true);
                    reopenActionMenu(p,editValuesMenus.actionMap.get(p));
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
        if(cooldownlist.contains(p)) {
            String name = e.getMessage();
            if(name.equalsIgnoreCase("cancel")) {
                matList.remove(p);
                e.setCancelled(true);
                reopenAbilityMenu(p);
            } else {
                boolean isnumber = isNumeric(name);
                if (isnumber) {
                    setPlayerStats.setDoubleBelow(p,"create","ability","cooldown", Double.valueOf(name));
                    cooldownlist.remove(p);
                    p.sendMessage("§a§lSet new Ability Cooldown to:§b " + name + "§6s");
                    e.setCancelled(true);
                    reopenAbilityMenu(p);
                } else {
                    p.sendMessage("§c§lThat is not a number!");
                    p.sendMessage("§c§lTry again!");
                }
            }
        }
    }

}
