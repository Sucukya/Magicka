package me.sucukya.abilities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.Magicka;
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
import org.bukkit.util.Vector;

import java.util.*;

public class Abilities implements Listener {

    public static List<String> cooldown = new ArrayList<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getOpenInventory().getType() != InventoryType.CHEST) {
                if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
                } else {
                    NBTItem nbti = new NBTItem(e.getItem());
                    if (nbti.getKeys().contains("stats")) {
                        NBTItem nbtiplayer = new NBTItem(p.getInventory().getItem(8));
                        NBTCompound nbtiplayerstats = nbtiplayer.getCompound("stats");
                        Double mana = nbtiplayerstats.getDouble("mana");
                        NBTCompound nbtistats = nbti.getCompound("stats");
                        NBTCompound ability = nbtistats.getCompound("ability");
                        NBTCompound abilityFunctions = nbtistats.getCompound("abilityFunction");
                        if ((mana - ability.getDouble("manacost")) < 0) {
                            p.sendMessage("§cYou don't have enough Mana!");
                        } else {
                            if (cooldown.contains(p.getDisplayName() + ability.getString("name"))) {
                                p.sendMessage("§cThis Ability is on Cooldown");
                            } else {
                                if(ability.getDouble("cooldown") == 0 || ability.getDouble("cooldown") == null) {
                                } else {
                                    cooldown.add(p.getDisplayName() + ability.getString("name"));
                                    startCooldown(ability.getDouble("cooldown"), p.getDisplayName() + ability.getString("name"), p);
                                }
                                for (int i = 0; i < abilityFunctions.getKeys().size(); i++) {
                                    int action = i + 1;
                                    useAbility(p, abilityFunctions.getCompound(String.valueOf(action)), ability);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void useAbility(Player p, NBTCompound ability, NBTCompound abilitycomp) {
        String type = ability.getString("type");
        NBTItem nbtiplayer = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtiplayerstats = nbtiplayer.getCompound("stats");
        Double mana = nbtiplayerstats.getDouble("mana");
        switch(type){
            case "teleport":
                Double range = ability.getDouble("range");
                for(double i = 0; i <= range; i++) {
                    Vector vec = p.getLocation().getDirection().multiply(i);
                    Location loc = p.getLocation().add(vec);
                    loc.setY(loc.getY() + 1);
                    if(loc.getBlock().getType().isSolid()) {
                        if(loc.getBlock().getY() -1 >= p.getLocation().getY()) {
                            p.sendMessage("§cThere are blocks in the way!");
                            i = range;
                            break;
                        } else {
                            loc.setY(loc.getY() + 1);
                            p.teleport(loc);
                            if(ability.getName().equalsIgnoreCase("1")) {
                                p.sendMessage("§aUsed §b" + abilitycomp.getString("name") + " (§6-" + abilitycomp.getDouble("manacost") + " §bMana)");
                                setPlayerStats.setDouble(p, "mana", mana - abilitycomp.getDouble("manacost"), "stats");
                            }
                            i = range;
                            break;
                        }
                    } else {
                        if(i == range) {
                            loc.setY(loc.getY() + 1);
                            p.teleport(loc);
                            if(ability.getName().equalsIgnoreCase("1")) {
                                p.sendMessage("§aUsed §b" + abilitycomp.getString("name") + " (§6-" + abilitycomp.getDouble("manacost") + " §bMana)");
                                setPlayerStats.setDouble(p, "mana", mana - abilitycomp.getDouble("manacost"), "stats");
                            }
                            i = range;
                            break;
                        }
                    }
                }
                break;
            case "changeStat":
                Double amount = ability.getDouble("amount");
                String stat = ability.getString("stat");
                if(ability.getName().equalsIgnoreCase("1")) {
                    p.sendMessage("§aUsed §b" + ability.getString("name") + " (§6-" + abilitycomp.getDouble("manacost") + " §bMana)");
                    setPlayerStats.setDouble(p, "mana", mana - abilitycomp.getDouble("manacost"), "stats");
                }
                if(nbtiplayerstats.getDouble(stat) + amount > nbtiplayerstats.getDouble("max" + stat)) {
                    setPlayerStats.setDouble(p, stat, nbtiplayerstats.getDouble("max" + stat), "stats");
                } else {
                    setPlayerStats.setDouble(p, stat, nbtiplayerstats.getDouble(stat) + amount, "stats");
                }
                break;
            case "applyEffect":

                break;
            case "damage":
                break;
        }
    }

    public static void startCooldown(double cooldown, String entry, Player p) {
        Bukkit.getScheduler().runTaskLater(Magicka.plugin, new Runnable() {
            @Override
            public void run() {
                Abilities.cooldown.remove(entry);
                p.sendMessage("Cooldown Gone");
            }
        },(long) cooldown * 20);
    }
}
