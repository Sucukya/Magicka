package me.sucukya.utility;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class setPlayerStats {

    public static void setDouble(Player p, String stat, Double value, String compound) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        nbtistats.setDouble(stat, value);
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }
    public static void setBoolean(Player p, String stat, Boolean value, String compound) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        nbtistats.setBoolean(stat, value);
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }
    public static void setString(Player p, String stat, String value, String compound) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        nbtistats.setString(stat, value);
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }
    public static void setStringBelow(Player p, String compound,String location, String stat, String value) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        NBTCompound nbtiloc = nbtistats.getCompound(location);
        nbtiloc.setString(stat, value);
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }
    public static void setDoubleBelow(Player p, String compound,String location, String stat, Double value) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        NBTCompound nbtiloc = nbtistats.getCompound(location);
        nbtiloc.setDouble(stat, value);
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }
    public static void setInteger(Player p, String stat, Integer value, String compound) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        nbtistats.setInteger(stat, value);
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }

    public static void setList(Player p, String stat, List<String> value, String compound) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        NBTCompound nbtilist = nbtistats.getCompound(stat);
        for(int i = 0; i < value.toArray().length ; i++) {
            String add = value.get(i);
            nbtilist.setString(String.valueOf(i),add);
        }
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }

    public static void setMap(Player p, String stat, HashMap<String,String> value, String compound) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtistats = nbti.getCompound(compound);
        NBTCompound nbtilist = nbtistats.getCompound(stat);
        for(int i = 0; i < value.size() ; i++) {
            String add = value.get(i);
            Set<String> keys = value.keySet();
            String key = String.valueOf(keys.stream().findFirst());
            keys.remove(key);
            nbtilist.setString(key,add);
        }
        ItemStack stack = nbti.getItem();
        p.getInventory().setItem(8,stack);
    }

}
