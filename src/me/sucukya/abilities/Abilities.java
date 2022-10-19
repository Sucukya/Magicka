package me.sucukya.abilities;

import de.tr7zw.nbtapi.NBTItem;
import me.sucukya.utility.getItemStats;
import me.sucukya.utility.getPlayerStats;
import me.sucukya.utility.setPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class Abilities implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getOpenInventory().getType() != InventoryType.CHEST) {
                if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
                } else {
                    if (new NBTItem(e.getItem()).getKeys().contains("hasAbility")) {
                        useAbility(p, e.getItem());
                    }
                }
            }
        }
    }

    public static void useAbility(Player p, ItemStack stack) {
        HashMap<String, Double> abilitystats = getItemStats.getAbilityStats(stack);
        HashMap<String, String> ability = getItemStats.getAbility(stack);
        HashMap<String, Double> playerstats = getPlayerStats.getStats(p);
        Double manacost = abilitystats.get("manacost");
        if (playerstats.get("mana") - manacost < 0) {
            p.sendMessage("§cNot enough mana!");
        } else {
            switch (ability.get("type")) {
                case "teleport":
                    Integer range = Integer.valueOf(ability.get("range"));
                    for(int i = 1; i <= range ; i++) {
                        Location loc2 = p.getLocation().add(p.getLocation().getDirection().multiply(i));
                        p.sendMessage(loc2.getBlock().getType().toString());
                        p.sendMessage(loc2.toString());
                        if(loc2.getBlock().getType() != Material.AIR) {
                            Location loc1 = p.getLocation().add(p.getLocation().getDirection().multiply(i));
                            Location loc = new Location(p.getWorld(), loc1.getX(), loc1.getY() + 1, loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                            p.teleport(loc);
                            setPlayerStats.setDouble(p, "mana", playerstats.get("mana") - manacost, "stats");
                            p.sendMessage("§aUsed§b " + ability.get("name") + " (§6-" + manacost + " §bMana)");
                            i = range;
                        } else {
                            if(i == range) {
                                Location loc1 = p.getLocation().add(p.getLocation().getDirection().multiply(i));
                                Location loc = new Location(p.getWorld(), loc1.getX(), loc1.getY() + 1, loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                                p.teleport(loc);
                                setPlayerStats.setDouble(p, "mana", playerstats.get("mana") - manacost, "stats");
                                p.sendMessage("§aUsed§b " + ability.get("name") + " (§6-" + manacost + " §bMana)");
                            }
                        }
                    }
                case "statbuff":

                        }
                    }

            }
}
