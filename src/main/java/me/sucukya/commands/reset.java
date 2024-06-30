package me.sucukya.commands;

import me.sucukya.Magicka;
import me.sucukya.utility.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class reset implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cms, Command cmd, String str, String[] args) {
        if(cms instanceof Player) {
            Player p = (Player) cms;
            if(p.hasPermission("magicka.reset")) {
                if(args.length == 1) {
                    String player = args[0];
                    Player target = Bukkit.getPlayer(player);
                    if(Magicka.isOnline.contains(target)) {
                        for(int i = 0; i < 40; i++) {
                            target.getInventory().setItem(i, new ItemStack(Material.AIR));
                        }
                        YamlConfiguration alrjoined = YamlConfiguration.loadConfiguration(Files.alrjoined);
                        alrjoined.set(target.getUniqueId().toString(), null);
                        try {
                            alrjoined.save(Files.alrjoined);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        target.kickPlayer("§cYou have been reset due to cheating / other \n If you believe this is an error, contact your Server Owner");
                    }
                } else {
                    p.sendMessage("§cPlease specify a player to reset /reset <player>");
                }
            }
        }
        return true;
    }
}
