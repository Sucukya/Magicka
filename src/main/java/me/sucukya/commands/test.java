package me.sucukya.commands;

import me.sucukya.utility.setPlayerStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {
        if(cms instanceof Player) {
            Player p = (Player) cms;
            if(p.hasPermission("magicka.test")) {
                setPlayerStats.setDouble(p,"mana",20.0, "stats");
            }
        }
        return true;
    }
}
