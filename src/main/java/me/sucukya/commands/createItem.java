package me.sucukya.commands;

import me.sucukya.creator.createMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class createItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {
        if(cms instanceof Player) {
            Player p = (Player) cms;
            if(p.hasPermission("magicka.create")) {
                createMenu.openMenu(p);
            }
        }


        return true;
    }
}
