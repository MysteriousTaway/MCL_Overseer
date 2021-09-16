package me.itsjeras.mcl_overseer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static me.itsjeras.mcl_overseer.MCL_Overseer.LoggerInstance;

public class Analytics implements Listener {

    static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static String path;
    static FileConfiguration config;

    public static void SetConfigFile() {
        File DataFile = new File(plugin.getDataFolder() , "/Analytics/" + Get.CurrentDate().replace("/", "_") + ".yml");
        if (DataFile.exists()) {
            path = "plugins/MCL_Overseer/Analytics/" + Get.CurrentDate().replace("/", "_") + ".yml";
            config = YamlConfiguration.loadConfiguration(DataFile);
        } else {
            String fileName = Get.CurrentDate().replace("/", "_");
            String message = "[> SetConfigFile Exception (ANALYTICS) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);

            CheckForDataFile();
        }
    }


    public static void CheckForDataFile(){
        File file = new File("plugins/MCL_Overseer/Analytics/", Get.CurrentDate().replace("/", "_") + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
                FileManager.writeToFile("/Analytics/" + Get.CurrentDate().replace("/", "_") + ".yml",
                        "Connection:"+
                                "\n  Player-Connects: 0"+
                                "\n  Player-Disconnects: 0"+
                                "\nHealth:"+
                                "\n  Health-Lost: 0"+
                                "\n  Health-Regenerated: 0"+
                                "\nBlocks:"+
                                "\n  Blocks-Broken: 0 "+
                                "\n  Blocks-Built: 0"+
                                "\nChat:"+
                                "\n  Messages-Sent: 0"+
                                "\n  Commands-Executed: 0"+
                                "\nPlayer:"+
                                "\n  Player-Deaths: 0"+
                                "\n  Experience-Received: 0"+
                                "\n  Advancements-Done: 0"+
                                "\n  Overworld-Entered-Count: 0"+
                                "\n  Nether-Entered-Count: 0"+
                                "\n  End-Entered-Count: 0"+
                                "\nEntity:"+
                                "\n  Entities-Spawned: 0"+
                                "\n  Entity-Died-Amount: 0"+
                                "\nChunks:"+
                                "\n  New-Chunks-Loaded: 0"
                );
            } catch (IOException exception) {
                String fileName = Get.CurrentDate().replace("/", "_");
                String message = "[> CheckForDataFile Exception (ANALYTICS)<] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Exception message: " + exception.getMessage();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            }
        }
    }

//    public static void Save() {
//        try {
//            config.save(path);
//        } catch (IOException exception) {
//            LoggerInstance.severe(exception.getMessage());
//        }
//    }

    // ANALYTICS:
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) throws IOException {
        if(event.getEntity() instanceof Player ) {
            double value = config.getInt("Health.Health-Lost");
            config.set("Health.Health-Lost", value + event.getDamage());
            config.save(path);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
        int value = config.getInt("Player.Player-Deaths");
        config.set("Player.Player-Deaths", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) throws IOException {
        int value = config.getInt("Connection.Player-Connects");
        config.set("Connection.Player-Connects", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onPlayerDisconnectEvent(PlayerQuitEvent event) throws IOException {
        int value = config.getInt("Connection.Player-Disconnects");
        config.set("Connection.Player-Disconnects", value + 1);
        config.save(path);
    }

    @EventHandler @Deprecated
    public void onPlayerChat(PlayerChatEvent event) throws IOException {
        int value = config.getInt("Chat.Messages-Sent");
        config.set("Chat.Messages-Sent", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onCommandExecution(PlayerCommandPreprocessEvent event) throws IOException {
        int value = config.getInt("Chat.Commands-Executed");
        config.set("Chat.Commands-Executed", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) throws IOException {
        int value = config.getInt("Entity.Entities-Spawned");
        config.set("Entity.Entities-Spawned", value + 1);
        config.save(path);
    }

    @EventHandler
    public void EntityRegainHealthEvent(EntityRegainHealthEvent event) throws IOException {
        if(event.getEntity() instanceof Player ) {
            double value = config.getDouble("Health.Health-Regenerated");
            config.set("Health.Health-Regenerated", value + event.getAmount());
            config.save(path);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws IOException {
        int value = config.getInt("Blocks.Blocks-Built");
        config.set("Blocks.Blocks-Built", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws IOException {
        int value = config.getInt("Blocks.Blocks-Broken");
        config.set("Blocks.Blocks-Broken", value + 1);
        config.save(path);
    }

    @EventHandler
    public void PlayerExpChangeEvent(PlayerExpChangeEvent event) throws IOException {
        int value = config.getInt("Player.Experience-Received");
        config.set("Player.Experience-Received", value + event.getAmount());
        config.save(path);
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) throws IOException {
        switch(event.getTo().getWorld().getName()) {
            case("world") -> {
                int value = config.getInt("Player.Overworld-Entered-Count");
                config.set("Player.Overworld-Entered-Count", value + 1 );
                config.save(path);
            }
            case("world_nether") -> {
                int value = config.getInt("Player.Nether-Entered-Count");
                config.set("Player.Nether-Entered-Count", value + 1 );
                config.save(path);
            }
            case("world_the_end") -> {
                int value = config.getInt("Player.End-Entered-Count");
                config.set("Player.End-Entered-Count", value + 1 );
                config.save(path);
            }
        }
    }

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent event) throws IOException {
        if(!(event.getEntity() instanceof Player)){
            int value = config.getInt("Entity.Entity-Died-Amount");
            config.set("Entity.Entity-Died-Amount", value + 1 );
            config.save(path);
        }
    }

    @EventHandler
    public void NewChunkLoadEvent(ChunkLoadEvent event) throws IOException {
        if(event.isNewChunk()) {
            int value = config.getInt("Chunks.New-Chunks-Loaded");
            config.set("Chunks.New-Chunks-Loaded", value + 1);
            config.save(path);
        }
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerAdvancementDoneEvent event) throws IOException {
        int value = config.getInt("Player.Advancements-Done");
        config.set("Player.Advancements-Done", value + 1);
        config.save(path);
    }
}
