package me.sucukya.abilities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;

public class editValues {

    public static void changeType(Player p, String action, String type) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtcreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
        NBTCompound abilityFunction = abilityFunctions.getCompound(action);
        abilityFunction.setString("type", type);
        abilityFunction.setString("damageType", "physical");
        abilityFunction.setString("potion", "health");
        abilityFunction.setString("stat", "mana");
        p.getInventory().setItem(8,nbti.getItem());
        editAction.openMenu(p,action);
    }
    public static void changeStat(Player p, String action, String stat) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtcreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtcreate.getCompound("abilityFunction");
        NBTCompound abilityFunction = abilityFunctions.getCompound(action);
        abilityFunction.setString("stat", stat);
        p.getInventory().setItem(8,nbti.getItem());
        editAction.openMenu(p,action);
    }

}
