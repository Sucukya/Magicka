package me.sucukya.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class getNbt implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {
            Player p = (Player) cms;
            if(p instanceof Player) {
                p.sendMessage("test");
                if (p.hasPermission("magicka.nbtView")) {
                    if (p.getInventory().getItemInMainHand() != null && !p.getInventory().getItemInMainHand().getType().isAir()) {
                        p.sendMessage("test");
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (item.hasItemMeta()) {
                            NBTItem nbti = new NBTItem(item);
                            p.sendMessage("§a" + nbti.toString());
                        }
                    }
                }
            }
        return true;
    }
}
