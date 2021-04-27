package me.itsjeras.mcl_overseer;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import java.util.Objects;

public class BlockManager implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        try {
            for (Material material : Get.getBannedBlockList()) {
                if (event.getBlock().getType() == material) {
                    // Get data:
                    Player player = event.getPlayer();
                    Block block = event.getBlock();
                    Location location = event.getBlock().getLocation();
                    World world = location.getWorld();
                    // Check if player does not have OP:
                    if (!player.isOp()) {
                        // World check:
                        assert world != null;
                        boolean legalityCheck = false;
                        if (Objects.requireNonNull(location.getWorld()).getName().equals("world")) {
                            if ((-8000 < location.getBlockX() && location.getBlockX() < 8000) && (-8000 < location.getBlockZ() && location.getBlockZ() < 8000)) {
                                legalityCheck = true;
                            }
                        } else if (location.getWorld().getName().equals("world_nether")) {
                            if ((-1000 < location.getBlockX() && location.getBlockX() < 1000) && (-1000 < location.getBlockZ() && location.getBlockZ() < 1000)) {
                                legalityCheck = true;
                            }
                        }

                        if (legalityCheck) {
                            // send message:
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "<[!!!]> You can't place this in a PvE zone!");
                            // Save report:
                            String message = "[BLOCK] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " BLOCK TYPE: " + block.getType() + " WORLD: " + world.getName() + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
                            String fileName = Get.CurrentDate().replace("/", "_");
                            FileManager.writeToFile("ForbiddenActivityLog/" + fileName + ".txt", message);
                        }
                        // is op
                    }
                }
                // for loop
            }
            // try catch
        } catch(Exception exception){
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            System.out.println("<[!!!]> Overseer could not log block place event!");
            if (event == null) {
                message = "(null) [> onBlockPlace Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > event was equal to null and therefore no further information could be logged!";
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n" + message);
            } else {
                message = "[> onBlockPlace Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + Objects.requireNonNull(((Player) event).getPlayer()).getDisplayName();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
            }
        }
        //void
    }
}
