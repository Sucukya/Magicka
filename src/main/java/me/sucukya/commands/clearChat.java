package me.sucukya.commands;

import me.sucukya.creator.createMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class clearChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {
        if(cms instanceof Player) {
            Player p = (Player) cms;
                for(int i = 0; i < 100; i++) {
                    p.sendMessage("");
                }
        }
        return true;
    }
}
