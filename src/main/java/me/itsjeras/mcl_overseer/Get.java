package me.itsjeras.mcl_overseer;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Get {
    public static int RandomNumInRange (int min, int max) {
        // Generates a random number in a specified range.
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public static String CurrentTime () {
        //return System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String CurrentDate () {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static EntityType[] getBannedEntityList() {
        return new EntityType[] {
                EntityType.ENDER_CRYSTAL,
                EntityType.PRIMED_TNT,
                EntityType.MINECART_TNT,
                EntityType.WITHER,
        };
    }
    public static Material[] getBannedBlockList() {
        return new Material[] {
                Material.TNT,
                Material.TNT_MINECART,
                Material.BEDROCK,
        };
    }
//    public static EntityType[] getBannedEntityList_Global() {
//        return new EntityType[] {
//
//        };
//    }
//    public static Material[] getBannedBlockList_Global() {
//        return new Material[] {
//
//        };
//    }
//    public static Material[] getBannedInteractionList_Global() {
//        return new Material[] {
//                Material.END_PORTAL_FRAME,
//        };
//    }
}
