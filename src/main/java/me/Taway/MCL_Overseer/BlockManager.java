package me.Taway.MCL_Overseer;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class BlockManager implements Listener {

    @EventHandler
    protected void onBlockPlace(BlockPlaceEvent event) {
        try {
            for (Material material : Get.getBannedBlockList()) {
                if (event.getBlock().getType() == material) {
                    // Get data:
                    Player player = event.getPlayer();
                    Block block = event.getBlock();
                    Location location = event.getBlock().getLocation();
                    World world = location.getWorld();
                    // Check if player does not have OP:
                    if (!player.isOp() && Get.isInPvE(location)) {
                        // send message:
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "<[!!!]> You can't place this in a PvE zone!");
                        // Save report:
                        String message = "[BLOCK] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " BLOCK TYPE: " + block.getType() + " WORLD: " + world.getName() + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
                        String fileName = Get.CurrentDate().replace("/", "_");
                        FileManager.writeToFile("ForbiddenActivityLog/" + fileName + ".txt", message);
                        // Punish player:
                        HonorManager.ChangeHonorValueOfPlayer(player, ConfigManager.BlockPlacementPenalty);
                        // is not op && is int pve
                    }
                }
                // for loop
            }
            // try catch
        } catch (Exception exception) {
            String message;
            MCL_Overseer.LoggerInstance.info("<[!!!]> Overseer could not log block place event!");
            if (event == null) {
                message = "(null) <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > event was equal to null and therefore no further information could be logged!";
            } else {
                message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + Objects.requireNonNull(((Player) event).getPlayer()).getDisplayName();
            }
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
        //void
    }
}
