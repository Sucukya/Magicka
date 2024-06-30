package me.sucukya;

import me.sucukya.abilities.*;
import me.sucukya.commands.*;
import me.sucukya.creator.*;
import me.sucukya.listeners.*;
import me.sucukya.player.ActionBarSend;
import me.sucukya.player.ManaRegen;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class Magicka extends JavaPlugin {
    public static List<Player> isOnline = new ArrayList<>();
    public static Magicka plugin;


    @Override
    public void onEnable(){
        plugin = this;
        System.out.println("Magicka is loading ---");
        System.out.println("Enjoy your magickal experience!");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new mainMenu(), this);
        pm.registerEvents(new Abilities(), this);
        pm.registerEvents(new createMenu(), this);
        pm.registerEvents(new cheatMenu(), this);
        pm.registerEvents(new loreEditor(), this);
        pm.registerEvents(new abilityEditor(), this);
        pm.registerEvents(new abilityCreator(), this);
        pm.registerEvents(new editAction(), this);
        pm.registerEvents(new editValuesMenus(), this);

        getCommand("gm").setExecutor(new gm());
        getCommand("safeclear").setExecutor(new safeClear());
        getCommand("reset").setExecutor(new reset());
        getCommand("test").setExecutor(new test());
        getCommand("testitem").setExecutor(new spawnTestItem());
        getCommand("infinitemana").setExecutor(new infinitemana());
        getCommand("create").setExecutor(new createItem());
        getCommand("edit").setExecutor(new editItem());
        getCommand("clearChat").setExecutor(new clearChat());
        getCommand("getNbt").setExecutor(new getNbt());

        runChecks();

    }

    public void runChecks() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            isOnline.add(p);
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                ActionBarSend.send();
            }
        }, 20L, 10L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                ManaRegen.regen();
            }
        }, 20L, 20L);
    }


    @Override
    public void onDisable(){
        System.out.println("Magicka is unloading ---");
        System.out.println("I hope you enjoyed your magickal experience!");
    }
}
