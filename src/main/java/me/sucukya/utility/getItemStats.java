package me.sucukya.utility;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class getItemStats {

    public static HashMap<String, Double> getAbilityStats(ItemStack stack) {
        HashMap<String, Double> stats = new HashMap<>();
        NBTItem nbti = new NBTItem(stack);
        NBTCompound nbtistats = nbti.getCompound("stats");


        for(String key : nbtistats.getKeys()) {
            stats.put(key, nbtistats.getDouble(key));
        }

        return stats;
    }
    public static HashMap<String, String> getAbility(ItemStack stack) {
        HashMap<String, String> stats = new HashMap<>();
        NBTItem nbti = new NBTItem(stack);
        NBTCompound nbtistats = nbti.getCompound("ability");


        for(String key : nbtistats.getKeys()) {
            stats.put(key, nbtistats.getString(key));
        }

        return stats;
    }

}
