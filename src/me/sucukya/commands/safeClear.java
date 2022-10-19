package me.sucukya.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class safeClear implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String str, String[] args) {

        if(cms instanceof Player) {
            Player p = (Player) cms;
            if (p.hasPermission("magicka.safeclear")) {
                for(int i = 0; i < 40 ; i++) {
                    if(i != 8) {
                        p.getInventory().setItem(i, new ItemStack(Material.AIR));
                    }
                }
                p.sendMessage("Safely cleared your inventory!");
            }
        }

        return true;
    }
}
