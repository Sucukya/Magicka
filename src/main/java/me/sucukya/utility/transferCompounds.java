package me.sucukya.utility;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;

public class transferCompounds {

    public static void transferCompound(NBTCompound cmp1, NBTCompound cmp2, NBTItem item1) {
        item1.removeKey(String.valueOf(cmp1));
        cmp1.mergeCompound(cmp2);
    }


}
