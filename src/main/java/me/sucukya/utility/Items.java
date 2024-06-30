package me.sucukya.utility;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Items {

    public static ItemStack createItemStack(Material material, int amount, int subid, String displayName) {
        ItemStack item = new ItemStack(material, amount, (short) subid);
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(displayName);
        item.setItemMeta(imeta);

        return item;
    }

    public static ItemStack createItemStackLore(Material material, int amount, int subid, String displayName, String loreLine) {
        ItemStack item = new ItemStack(material, amount, (short) subid);
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(loreLine);
        imeta.setLore(lore);
        item.setItemMeta(imeta);

        return item;
    }
    public static ItemStack createItemStackLoreNBT(Material material, int amount, int subid, String displayName, String loreLine,String key, String value) {
        ItemStack item = new ItemStack(material, amount, (short) subid);
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(loreLine);
        imeta.setLore(lore);
        item.setItemMeta(imeta);
        NBTItem nbti = new NBTItem(item);
        nbti.setString(key,value);
        item = nbti.getItem();

        return item;
    }
    public static ItemStack createItemStackListLore(Material material, int amount, int subid, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) subid);
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(displayName);
        imeta.setLore(lore);
        item.setItemMeta(imeta);

        return item;
    }
    public static ItemStack createHead(String url) {
        ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        if (url.isEmpty()) return head;
        return head;
    }
}
