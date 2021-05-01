package me.itsjeras.mcl_overseer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class EntityManager implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        try {
            // Find if spawned entity is on banned list:
            for (EntityType entity : Get.getBannedEntityList()) {
                if (entity == event.getEntityType()) {
                    boolean HoldsSpawnItem = false;
                    // Get location and world:
                    Location location = event.getLocation();
                    World world = event.getLocation().getWorld();
                    // Location is in PvE area:
                    if (Get.isInPvE(location)) {
                        for(Player player : Get.getNearbyPlayerList(event.getEntity())) {
                            if (!player.isOp()) {
                                event.setCancelled(true);
                                for(Material item : Get.getBannedEntitySpawnItem()) {
                                    if (player.getInventory().contains(item)) {
                                        HoldsSpawnItem = true;
                                    }
                                    if(HoldsSpawnItem) {
                                        player.sendMessage(ChatColor.RED + "<[!!!]> You can't do this in a PvE zone!");
                                        String message = "[ENTITY] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " BLOCK TYPE: " + entity + " WORLD: " + world.getName() + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
                                        String fileName = Get.CurrentDate().replace("/", "_");
                                        FileManager.writeToFile("ForbiddenActivityLog/" + fileName + ".txt", message);
                                        // Punish:
                                        HonorManager.ChangeHonorValueOfPlayer(player, ConfigManager.EntitySpawnPenalty);
                                        break;
                                    }
                                }
                            }
                        }
                        // if entity is in PvE in "world"
                    }
                    // if entity is banned
                }
                // for loop
            }
            // try catch loop
        } catch (Exception exception){
            System.out.println("<[!!!]> Overseer could not log entity spawn event!");
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            if (event == null) {
                message = "(null) [> onEntitySpawn Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > event was equal to null and therefore no further information could be logged!";
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt","\n" + message);
            } else {
                message = "[> onEntitySpawn Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > World: " + Objects.requireNonNull(event.getLocation().getWorld()).getName();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
            }
        }
        // void
    }
}
//    @EventHandler
//    public void MinecartSpawnEvent(VehicleCreateEvent event) {
//        try {
//            if (event != null) {
//
//            }
//        } catch(Exception exception) {
//            String fileName = Get.CurrentDate().replace("/", "_");
//            String message = "[> MinecartSpawnEvent Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > World: " + event.getVehicle().getLocation().getWorld().toString();
//            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
//            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
//        }
//    }