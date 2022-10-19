package me.sucukya.utility;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

}
