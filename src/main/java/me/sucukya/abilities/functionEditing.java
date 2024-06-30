package me.sucukya.abilities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;

public class functionEditing {

    public static void deleteFunction(Player p, String action) {
        int act = Integer.parseInt(action);
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtCreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtCreate.getCompound("abilityFunction");
        if(act == abilityFunctions.getKeys().size()) {
            abilityFunctions.removeKey(action);
        } else {
            for (int i = 0; i < abilityFunctions.getKeys().size(); i++) {
                int ii = i + 2;
                if (ii > act) {
                    NBTCompound abilityFunction = abilityFunctions.getCompound(ii + "");
                    String type = abilityFunction.getString("type");
                    String damageType = "";
                    String potion = "";
                    String stat = "";
                    double range = 0;
                    double damage = 0;
                    double level = 0;
                    double duration = 0;
                    double amount = 0;
                    boolean selfApply = false;
                    if (abilityFunction.getKeys().contains("range")) {
                        range = abilityFunction.getDouble("range");
                    }
                    if (abilityFunction.getKeys().contains("damageType")) {
                        damageType = abilityFunction.getString("damageType");
                    }
                    if (abilityFunction.getKeys().contains("damage")) {
                        damage = abilityFunction.getDouble("damage");
                    }
                    if (abilityFunction.getKeys().contains("selfApply")) {
                        selfApply = abilityFunction.getBoolean("selfApply");
                    }
                    if (abilityFunction.getKeys().contains("level")) {
                        level = abilityFunction.getDouble("level");
                    }
                    if (abilityFunction.getKeys().contains("duration")) {
                        duration = abilityFunction.getDouble("duration");
                    }
                    if (abilityFunction.getKeys().contains("potion")) {
                        potion = abilityFunction.getString("potion");
                    }
                    if (abilityFunction.getKeys().contains("stat")) {
                        stat = abilityFunction.getString("stat");
                    }
                    if (abilityFunction.getKeys().contains("amount")) {
                        amount = abilityFunction.getDouble("amount");
                    }
                    abilityFunctions.removeKey(ii + "");
                    NBTCompound abilityFunctionNew = abilityFunctions.addCompound(i + 1 + "");
                    abilityFunctionNew.setString("type", type);
                    abilityFunctionNew.setString("damageType", damageType);
                    abilityFunctionNew.setString("potion", potion);
                    abilityFunctionNew.setString("stat", stat);
                    abilityFunctionNew.setDouble("range", range);
                    abilityFunctionNew.setDouble("damage", damage);
                    abilityFunctionNew.setDouble("level", level);
                    abilityFunctionNew.setDouble("duration", duration);
                    abilityFunctionNew.setDouble("amount", amount);
                    abilityFunctionNew.setBoolean("selfApply", selfApply);

                }
            }
        }
        p.getInventory().setItem(8, nbti.getItem());
        p.sendMessage(abilityFunctions.getKeys().toString());
        abilityCreator.openMenu(p);
    }
    public static void addFunction(Player p) {
        NBTItem nbti = new NBTItem(p.getInventory().getItem(8));
        NBTCompound nbtCreate = nbti.getCompound("create");
        NBTCompound abilityFunctions = nbtCreate.getCompound("abilityFunction");
        int action = abilityFunctions.getKeys().size() + 1;
        NBTCompound abilityFunction = abilityFunctions.addCompound( action + "");
        abilityFunction.setString("type","teleport");
        abilityFunction.setDouble("range",8.0);
        p.getInventory().setItem(8,nbti.getItem());
        editAction.openMenu(p, String.valueOf(action));
    }
}
