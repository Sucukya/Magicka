package me.sucukya.commands;

import me.sucukya.utility.getPlayerStats;
import me.sucukya.utility.setPlayerStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class infinitemana implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String arg, String[] args) {

        if (cms instanceof Player) {
            Player p = (Player) cms;
            if (p.hasPermission("magicka.infinitemana")) {
                infinitemana(p);
            }
        }
            return true;
        }

        public static void infinitemana(Player p) {
            HashMap<String, Boolean> cheats = getPlayerStats.getCheats(p);
            HashMap<String, Double> base = getPlayerStats.getBaseStats(p);

            if(cheats.get("infinitemana")) {
                setPlayerStats.setBoolean(p,"infinitemana",false,"cheats");
                setPlayerStats.setDouble(p,"mana", base.get("mana"), "stats");
                setPlayerStats.setDouble(p,"maxmana", base.get("maxmana"), "stats");
                p.sendMessage("§bTurned Infinite Mana §c§lOFF");
            } else {
                setPlayerStats.setBoolean(p,"infinitemana",true,"cheats");
                setPlayerStats.setDouble(p,"mana", 1000000.0, "stats");
                setPlayerStats.setDouble(p,"maxmana", 1000000.0, "stats");
                p.sendMessage("§bTurned Infinite Mana §a§lON");
            }
        }
}
