package me.sucukya.commands;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.sucukya.creator.createMenu;
import me.sucukya.utility.transferCompounds;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class editItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {
        if(cms instanceof Player) {
            Player p = (Player) cms;
            NBTItem star = new NBTItem(p.getInventory().getItem(8));
            ItemStack hand = p.getInventory().getItemInMainHand();
            NBTItem item = null;
            if(hand.hasItemMeta()) {
                item = new NBTItem(hand);
            }
            NBTCompound create = star.getCompound("create");
            NBTCompound stats = item.getCompound("stats");

            if(p.hasPermission("magicka.create")) {
                transferCompounds.transferCompound(create,stats,star);
                p.getInventory().setItem(8, star.getItem());
                createMenu.openMenu(p);
            }
        }




        return true;
    }
}
