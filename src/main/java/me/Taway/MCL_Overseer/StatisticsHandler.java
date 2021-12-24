package me.Taway.MCL_Overseer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;
import static me.Taway.MCL_Overseer.MCL_Overseer.ServerInstance;

public class StatisticsHandler extends BukkitRunnable implements Listener {
    private Collection<Player> OnlinePlayers = new ArrayList<>();
    // Runnable calculator thingy:
    @Override
    public void run() {
        try {
            OnlinePlayers = null;
            OnlinePlayers = (Collection<Player>) ServerInstance.getOnlinePlayers();
            for (Player player: OnlinePlayers) {
                // Self-explanatory variable names ☻
                String World = player.getWorld().getName();
                int X = player.getLocation().getBlockX();
                int Z = player.getLocation().getBlockZ();
                // Get dimension:
                switch(World.toLowerCase()) {
                    case("world") -> {
                        if(X > 0 && Z > 0) {
                            StatisticsManager.Overworld_X_plus_Z_plus++;
                        } else if (X < 0 && Z > 0) {
                            StatisticsManager.Overworld_X_minus_Z_plus++;
                        } else if (X > 0 && Z < 0) {
                            StatisticsManager.Overworld_X_plus_Z_minus++;
                        }  else if (X < 0 && Z < 0) {
                            StatisticsManager.Overworld_X_minus_Z_minus++;
                        } else {
                            String fileName = Get.CurrentDate().replace("/", "_");
                            String message = "[> StatisticsHandler (run) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Message: Player (" + player.getDisplayName() + ") is on coordinates X=0 and Z=0 and cannot be added to  a quadrant!";
                            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
                        }
                    }
                    case("world_nether") -> {
                        if(X > 0 && Z > 0) {
                            StatisticsManager.Nether_X_plus_Z_plus++;
                        } else if (X < 0 && Z > 0) {
                            StatisticsManager.Nether_X_minus_Z_plus++;
                        } else if (X > 0 && Z < 0) {
                            StatisticsManager.Nether_X_plus_Z_minus++;
                        }  else if (X < 0 && Z < 0) {
                            StatisticsManager.Nether_X_minus_Z_minus++;
                        } else {
                            String fileName = Get.CurrentDate().replace("/", "_");
                            String message = "[> StatisticsHandler (run) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Message: Player (" + player.getDisplayName() + ") is on coordinates X=0 and Z=0 and cannot be added to  a quadrant!";
                            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
                        }
                    }
                    case("world_the_end") -> {
                        if(X > 0 && Z > 0) {
                            StatisticsManager.End_X_plus_Z_plus++;
                        } else if (X < 0 && Z > 0) {
                            StatisticsManager.End_X_minus_Z_plus++;
                        } else if (X > 0 && Z < 0) {
                            StatisticsManager.End_X_plus_Z_minus++;
                        }  else if (X < 0 && Z < 0) {
                            StatisticsManager.End_X_minus_Z_minus++;
                        } else {
                            String fileName = Get.CurrentDate().replace("/", "_");
                            String message = "[> StatisticsHandler (run) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Message: Player (" + player.getDisplayName() + ") is on coordinates X=0 and Z=0 and cannot be added to  a quadrant!";
                            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            LoggerInstance.severe("BukkitRunnable ERROR while running timed statistics operation ! " + exception.getMessage());
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to run timed statistics operation! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    //  Statistics:
    @EventHandler
    protected void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            StatisticsManager.Health_Lost += event.getDamage();
        }
    }

    @EventHandler
    protected void onPlayerDeath(PlayerDeathEvent event) {
        StatisticsManager.Player_Deaths++;
    }

    @EventHandler
    protected void onPlayerJoinEvent(PlayerJoinEvent event) {
        StatisticsManager.Player_Connects++;
    }

    @EventHandler
    protected void onPlayerDisconnectEvent(PlayerQuitEvent event) {
        StatisticsManager.Player_Disconnects++;
    }

    @EventHandler
    @Deprecated
    protected void onPlayerChat(PlayerChatEvent event) {
        StatisticsManager.Messages_Sent++;
    }

    @EventHandler
    protected void onCommandExecution(PlayerCommandPreprocessEvent event) {
        StatisticsManager.Commands_Executed++;
    }

    @EventHandler
    protected void onEntitySpawn(EntitySpawnEvent event) {
        StatisticsManager.Entities_Spawned++;
    }

    @EventHandler
    protected void EntityRegainHealthEvent(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            StatisticsManager.Health_Regenerated += event.getAmount();
        }
    }

    @EventHandler
    protected void onBlockPlace(BlockPlaceEvent event) {
        StatisticsManager.Blocks_Built++;
    }

    @EventHandler
    protected void onBlockBreak(BlockBreakEvent event) {
        StatisticsManager.Blocks_Broken++;
    }

    @EventHandler
    protected void PlayerExpChangeEvent(PlayerExpChangeEvent event) {
        StatisticsManager.Experience_Received += event.getAmount();
    }

    @EventHandler
    protected void PlayerTeleportEvent(PlayerTeleportEvent event) {
        switch (event.getTo().getWorld().getName()) {
            case ("world") -> StatisticsManager.Overworld_Entered_Count++;
            case ("world_nether") -> StatisticsManager.Nether_Entered_Count++;
            case ("world_the_end") -> StatisticsManager.End_Entered_Count++;
        }
    }

    @EventHandler
    protected void EntityDeathEvent(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            StatisticsManager.Entity_Died_Amount++;
        }
    }

    @EventHandler
    protected void NewChunkLoadEvent(ChunkLoadEvent event) {
        if(event.isNewChunk()) {
            StatisticsManager.New_Chunks_Loaded++;
            switch (event.getWorld().getName()) {
                case ("world") -> StatisticsManager.Overworld_New_Chunk_Count++;
                case ("world_nether") -> StatisticsManager.Nether_New_Chunk_Count++;
                case ("world_the_end") -> StatisticsManager.End_New_Chunk_Count++;
            }
        }
    }

    @EventHandler
    protected void PlayerInteractEvent(PlayerAdvancementDoneEvent event) {
        StatisticsManager.Advancements_Done++;
    }
}
