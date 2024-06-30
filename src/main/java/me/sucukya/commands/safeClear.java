package me.sucukya.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class safeClear implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String str, String[] args) {

        if(cms instanceof Player) {
            Player p = (Player) cms;
            if (p.hasPermission("magicka.safeclear")) {
                for(int i = 0; i < 40 ; i++) {
                    if(p.getInventory().getItem(i) != null && !p.getInventory().getItem(8).getType().isAir()) {
                        ItemStack item = p.getInventory().getItem(i);
                        if(item.hasItemMeta()) {
                            ItemMeta meta = item.getItemMeta();
                            NBTItem nbti = new NBTItem(item);
                            if(nbti.getKeys().contains("stats")) {                            }
                        } else {
                            p.getInventory().setItem(i,new ItemStack(Material.AIR));
                        }
                    }
                }
                p.sendMessage("Safely cleared your inventory!");
            }
        }

        return true;
    }
}
