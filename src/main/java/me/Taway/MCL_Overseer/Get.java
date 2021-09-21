package me.Taway.MCL_Overseer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class Get {
    protected static int RandomNumInRange(int min, int max) {
        // Generates a random number in a specified range.
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    protected static String CurrentTime() {
        //return System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    protected static String CurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    protected static EntityType[] getBannedEntityList() {
        return new EntityType[]{
                EntityType.ENDER_CRYSTAL,
                EntityType.PRIMED_TNT,
                EntityType.WITHER,
        };
    }

    protected static Material[] getBannedBlockList() {
        return new Material[]{
                Material.TNT,
        };
    }

    protected static Material[] getBannedEntitySpawnItem() {
        return new Material[]{
                Material.SOUL_SAND,
                Material.END_CRYSTAL,
                Material.WITHER_SKELETON_SKULL,
                Material.TNT_MINECART,
        };
    }

    protected static EntityType[] getBannedMinecartList() {
        return new EntityType[]{
                EntityType.MINECART_TNT
        };
    }

    protected static boolean isInPvE(Location location) {
        if (Objects.requireNonNull(location.getWorld()).getName().equals("world")) {
            return (-ConfigManager.OverworldPVE_X < location.getBlockX() && location.getBlockX() < ConfigManager.OverworldPVE_X) && (-ConfigManager.OverworldPVE_Z < location.getBlockZ() && location.getBlockZ() < ConfigManager.OverworldPVE_Z);
        } else if (location.getWorld().getName().equals("world_nether")) {
            return (-ConfigManager.NetherPVE_X < location.getBlockX() && location.getBlockX() < ConfigManager.NetherPVE_X) && (-ConfigManager.NetherPVE_Z < location.getBlockZ() && location.getBlockZ() < ConfigManager.NetherPVE_Z);
        }
        return false;
    }

    protected static List<Entity> getNearbyEntityList(Entity entity) {
        List<Entity> entities = null;
        for (int i = 0; i < 256; i++) {
            entities = entity.getNearbyEntities(i, 16, i);
        }
//        System.out.println(entities);
        LoggerInstance.info((Supplier<String>) entities);
        return entities;
    }

    protected static List<Player> getNearbyPlayerList(Entity entity) {
        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            double distance;
            Location playerLoc = p.getLocation();
            Location entityLoc = entity.getLocation();
            distance = playerLoc.distance(entityLoc);
            if (distance < 32) {
                players.add(p);
            }
        }
        return players;
    }
//    protected static EntityType[] getBannedEntityList_Global() {
//        return new EntityType[] {
//
//        };
//    }
//    protected static Material[] getBannedBlockList_Global() {
//        return new Material[] {
//
//        };
//    }
//    protected static Material[] getBannedInteractionList_Global() {
//        return new Material[] {
//                Material.END_PORTAL_FRAME,
//        };
//    }
}