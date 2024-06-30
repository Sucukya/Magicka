package me.sucukya.commands;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class spawnTestItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {
        if(cms instanceof Player) {
            Player p = (Player) cms;
            if(p.hasPermission("magicka.testitem")) {
                ItemStack testitem = new ItemStack(Material.STICK);
                ItemMeta testmeta = testitem.getItemMeta();
                testmeta.setDisplayName("§aTest-Sthick");
                testitem.setItemMeta(testmeta);
                NBTItem nbti = new NBTItem(testitem);
                nbti.setBoolean("hasAbility", true);
                NBTCompound nbtistats = nbti.addCompound("stats");
                NBTCompound nbtiability = nbti.addCompound("ability");
                nbtistats.setDouble("manacost", 50.0);
                nbtistats.setDouble("dmgscale", 2.0);
                nbtiability.setString("name", "Test");
                nbtiability.setString("type", "teleport");
                nbtiability.setString("range", "5");
                testitem = nbti.getItem();
                p.getInventory().addItem(testitem);
            }
        }

        return true;
    }
}
