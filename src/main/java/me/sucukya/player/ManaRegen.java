package me.sucukya.player;

import me.sucukya.utility.getPlayerStats;
import me.sucukya.utility.setPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ManaRegen {

    public static void regen() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            HashMap<String, Double> stats = getPlayerStats.getStats(p);
            double manaregen = Math.floor(stats.get("maxmana") / 33);
            HashMap<String, Boolean> cheats = getPlayerStats.getCheats(p);
            if(cheats.get("infinitemana")) {
                manaregen = 2147483647;
            }
            if(stats.get("mana") + manaregen > stats.get("maxmana")) {
                setPlayerStats.setDouble(p,"mana",stats.get("maxmana"),"stats");
            } else {
                setPlayerStats.setDouble(p,"mana",stats.get("mana") + manaregen, "stats");
            }

        }
    }

}
