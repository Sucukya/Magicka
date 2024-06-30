package me.sucukya.utility;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.Magicka;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class getPlayerStats {

    public static HashMap<String, Double> getStats(Player p) {
        if (Magicka.isOnline.contains(p)) {
            if (Files.alrjoined.exists()) {
                YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                if (alrjoined.contains(p.getUniqueId().toString())) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtistats = nbti.getCompound("stats");
                    HashMap<String, Double> stats = new HashMap<>();

                    for (String key : nbtistats.getKeys()) {
                        stats.put(key, nbtistats.getDouble(key));
                    }

                    return stats;
                }
            }
        }
        return null;
    }

    public static HashMap<String, Double> getBaseStats(Player p) {
        if (Magicka.isOnline.contains(p)) {
            if (Files.alrjoined.exists()) {
                YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                if (alrjoined.contains(p.getUniqueId().toString())) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbtistats = nbti.getCompound("base");
                    HashMap<String, Double> stats = new HashMap<>();

                    for (String key : nbtistats.getKeys()) {
                        stats.put(key, nbtistats.getDouble(key));
                    }

                    return stats;
                }
            }
        }
        return null;
    }

    public static HashMap<String, Boolean> getCheats(Player p) {
        if (Magicka.isOnline.contains(p)) {
            if (Files.alrjoined.exists()) {
                YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                if (alrjoined.contains(p.getUniqueId().toString())) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbticheats = nbti.getCompound("cheats");
                    HashMap<String, Boolean> cheats = new HashMap<>();

                    for (String key : nbticheats.getKeys()) {
                        cheats.put(key, nbticheats.getBoolean(key));
                    }

                    return cheats;
                }
            }
        }
        return null;
    }

    public static HashMap<String, String> getCreate(Player p) {
        if (Magicka.isOnline.contains(p)) {
            if (Files.alrjoined.exists()) {
                YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                if (alrjoined.contains(p.getUniqueId().toString())) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbticheats = nbti.getCompound("create");
                    HashMap<String, String> cheats = new HashMap<>();

                    for (String key : nbticheats.getKeys()) {
                        cheats.put(key, nbticheats.getString(key));
                    }

                    return cheats;
                }
            }
        }
        return null;
    }

    public static List<String> getList(Player p, String key, String compound) {
        if (Magicka.isOnline.contains(p)) {
            if (Files.alrjoined.exists()) {
                YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                if (alrjoined.contains(p.getUniqueId().toString())) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbticheats = nbti.getCompound(compound);
                    NBTCompound nbtilist = nbticheats.getCompound(key);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < nbtilist.getKeys().toArray().length; i++) {
                        String add = nbtilist.getString(String.valueOf(i));
                        list.add(add);
                    }
                    return list;
                }
            }
        }
        return null;
    }

    public static HashMap<String, String> getMap(Player p, String location, String compound) {
        if (Magicka.isOnline.contains(p)) {
            if (Files.alrjoined.exists()) {
                YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                if (alrjoined.contains(p.getUniqueId().toString())) {
                    NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
                    NBTCompound nbticheats = nbti.getCompound(compound);
                    NBTCompound nbtilist = nbticheats.getCompound(location);
                    HashMap<String, String> map = new HashMap<>();
                    for (String key : nbtilist.getKeys()) {
                        map.put(key, nbtilist.getString(key));
                    }
                    return map;
                }
            }
        }
        return null;
    }

    public static NBTCompound getAbilityFunctionsComp(Player p) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtCreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtCreate.getCompound("abilityFunction");
        return  abilityFunctions;
    }

}
