package me.sucukya.player;

import me.sucukya.utility.ActionBar;
import me.sucukya.utility.getPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ActionBarSend implements Listener {

    public static void send() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            HashMap<String, Double> stats = getPlayerStats.getStats(p);
            double mana = stats.get("mana");
            double maxmana = stats.get("maxmana");

            ActionBar.sendPacket(p, "§b " + mana + "/" + maxmana + " §bMana");
        }
    }

}
