package me.sucukya.listeners;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.Magicka;
import me.sucukya.player.ActionBarSend;
import me.sucukya.utility.Files;
import me.sucukya.utility.setPlayerStats;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Magicka.isOnline.add(p);
            //Working with alreadJoined File
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(Files.alrjoined);
            if(conf.contains(p.getUniqueId().toString())) {
                ActionBarSend.send();
            } else {
                //Give Player Menu item
                ItemStack menu = new ItemStack(Material.NETHER_STAR);
                ItemMeta menumeta = menu.getItemMeta();
                menumeta.setDisplayName("§aMenu");
                menu.setItemMeta(menumeta);
                NBTItem nbti = new NBTItem(menu);
                NBTCompound nbtistats = nbti.addCompound("stats");
                NBTCompound nbticheats = nbti.addCompound("cheats");
                NBTCompound nbticreate = nbti.addCompound("create");
                NBTCompound nbtibase = nbti.addCompound("base");
                NBTCompound nbtilore = nbticreate.addCompound("lore");
                NBTCompound nbtiability = nbticreate.addCompound("ability");
                NBTCompound nbtiabilityFunctionComp = nbticreate.addCompound("abilityFunction");
                NBTCompound nbtiabilityFunctionExample = nbtiabilityFunctionComp.addCompound("1");
                nbtistats.setDouble("mana", 100.0);
                nbtistats.setDouble("maxmana", 100.0);
                nbtibase.setDouble("mana", 100.0);
                nbtibase.setDouble("maxmana", 100.0);
                nbticheats.setBoolean("infinitemana" , false);
                nbticreate.setString("name","null");
                nbticreate.setString("material","null");
                nbticreate.setString("sound", "null");
                nbtiability.setString("manacost","null");
                nbtiability.setString("cooldown","null");
                nbtiability.setString("name","null");
                nbtiabilityFunctionExample.setString("type","teleport");
                nbtiabilityFunctionExample.setDouble("range", 8.0);
                List<String> lore = new ArrayList<>();
                lore.add("§a Example line 1");
                lore.add("§a Example line 2");

                menu = nbti.getItem();
                p.getInventory().setItem(8, menu);
                p.sendMessage("Gave Menu Item");
                setPlayerStats.setList(p,"lore",lore,"create");

                conf.set(p.getUniqueId().toString(), 1);
                try {
                    conf.save(Files.alrjoined);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

}
