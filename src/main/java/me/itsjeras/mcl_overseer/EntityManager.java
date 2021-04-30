package me.itsjeras.mcl_overseer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;
import java.util.Objects;

public class EntityManager implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        try {
            // Find if spawned entity is on banned list:
            for (EntityType entity : Get.getBannedEntityList()) {
                if (entity == event.getEntityType()) {
                    // Create player:
                    Player player = null;
                    // Get location and world:
                    Location location = event.getLocation();
                    World world = event.getLocation().getWorld();
                    // Get if Spawned entity is in 8kx8k spawn area in overworld or 1kx1k are in the nether
                    assert world != null;
                    boolean legalityCheck = false;
                    if (Objects.requireNonNull(location.getWorld()).getName().equals("world")) {
                        if ((-ConfigManager.OverworldPVE_X < location.getBlockX() && location.getBlockX() < ConfigManager.OverworldPVE_X) && (-ConfigManager.OverworldPVE_Z < location.getBlockZ() && location.getBlockZ() < ConfigManager.OverworldPVE_Z)) {
                            legalityCheck = true;
                        }
                    } else if (location.getWorld().getName().equals("world_nether")) {
                        if ((-ConfigManager.NetherPVE_X < location.getBlockX() && location.getBlockX() < ConfigManager.NetherPVE_X) && (-ConfigManager.NetherPVE_Z < location.getBlockZ() && location.getBlockZ() < ConfigManager.NetherPVE_Z)) {
                            legalityCheck = true;
                        }
                    }
                    // Location is in PvE area:
                    if (legalityCheck) {
                        // Get closest player:
                        boolean found = false;
                        for (int i = 0; i < 200; i++) {
                            List<Entity> entities = event.getEntity().getNearbyEntities(i, 16, i);
                            for (Entity e : entities) {
                                if (e.getType().equals(EntityType.PLAYER)) {
                                    player = (Player) e;
                                    found = true;
                                    break;
                                }
                            }
                            if (found) break;
                        }

                        // If player is found:
                        if (found) {
                            // Event logic:
                            if (!player.isOp()) {
                                event.setCancelled(true);
                                player.sendMessage(ChatColor.RED + "<[!!!]> You can't do this in a PvE zone!");
                                String message = "[ENTITY] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " BLOCK TYPE: " + entity + " WORLD: " + world.getName() + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
                                String fileName = Get.CurrentDate().replace("/", "_");
                                FileManager.writeToFile("ForbiddenActivityLog/" + fileName + ".txt", message);
                                // Punish:
                                HonorManager.ChangeHonorValueOfPlayer(player, ConfigManager.EntitySpawnPenalty);
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
