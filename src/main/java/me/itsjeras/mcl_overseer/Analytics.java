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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

import static me.itsjeras.mcl_overseer.MCL_Overseer.LoggerInstance;

public class Analytics extends BukkitRunnable implements Listener {

    static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static String path;
    static FileConfiguration config;

    int Player_Connects = 0;
    int Player_Disconnects = 0;

    double Health_Lost = 0;
    double Health_Regenerated = 0;

    int Blocks_Broken = 0;
    int Blocks_Built = 0;

    int Messages_Sent = 0;
    int Commands_Executed = 0;

    int Player_Deaths = 0;
    int Experience_Received = 0;
    int Advancements_Done = 0;
    int Overworld_Entered_Count = 0;
    int Nether_Entered_Count = 0;
    int End_Entered_Count = 0;

    int Entities_Spawned = 0;
    int Entity_Died_Amount = 0;

    int New_Chunks_Loaded = 0;


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

    // ANALYTICS:
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player ) {
//            double value = config.getInt("Health.Health-Lost");
//            config.set("Health.Health-Lost", value + event.getDamage());
//            config.save(path);
            Health_Lost += event.getDamage();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
//        int value = config.getInt("Player.Player-Deaths");
//        config.set("Player.Player-Deaths", value + 1);
//        config.save(path);
        Player_Deaths++;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
//        int value = config.getInt("Connection.Player-Connects");
//        config.set("Connection.Player-Connects", value + 1);
//        config.save(path);
        Player_Connects++;
    }

    @EventHandler
    public void onPlayerDisconnectEvent(PlayerQuitEvent event) {
//        int value = config.getInt("Connection.Player-Disconnects");
//        config.set("Connection.Player-Disconnects", value + 1);
//        config.save(path);
        Player_Disconnects++;
    }

    @EventHandler @Deprecated
    public void onPlayerChat(PlayerChatEvent event) {
//        int value = config.getInt("Chat.Messages-Sent");
//        config.set("Chat.Messages-Sent", value + 1);
//        config.save(path);
        Messages_Sent++;
    }

    @EventHandler
    public void onCommandExecution(PlayerCommandPreprocessEvent event) {
//        int value = config.getInt("Chat.Commands-Executed");
//        config.set("Chat.Commands-Executed", value + 1);
//        config.save(path);
        Commands_Executed++;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
//        int value = config.getInt("Entity.Entities-Spawned");
//        config.set("Entity.Entities-Spawned", value + 1);
//        config.save(path);
        Entities_Spawned++;
    }

    @EventHandler
    public void EntityRegainHealthEvent(EntityRegainHealthEvent event) {
        if(event.getEntity() instanceof Player ) {
//            double value = config.getDouble("Health.Health-Regenerated");
//            config.set("Health.Health-Regenerated", value + event.getAmount());
//            config.save(path);
            Health_Regenerated += event.getAmount();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
//        int value = config.getInt("Blocks.Blocks-Built");
//        config.set("Blocks.Blocks-Built", value + 1);
//        config.save(path);
        Blocks_Built++;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
//        int value = config.getInt("Blocks.Blocks-Broken");
//        config.set("Blocks.Blocks-Broken", value + 1);
//        config.save(path);
        Blocks_Broken++;
    }

    @EventHandler
    public void PlayerExpChangeEvent(PlayerExpChangeEvent event) {
//        int value = config.getInt("Player.Experience-Received");
//        config.set("Player.Experience-Received", value + event.getAmount());
//        config.save(path);
        Experience_Received += event.getAmount();
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) {
        switch(event.getTo().getWorld().getName()) {
            case("world") -> {
//                int value = config.getInt("Player.Overworld-Entered-Count");
//                config.set("Player.Overworld-Entered-Count", value + 1 );
//                config.save(path);
                Overworld_Entered_Count++;
            }
            case("world_nether") -> {
//                int value = config.getInt("Player.Nether-Entered-Count");
//                config.set("Player.Nether-Entered-Count", value + 1 );
//                config.save(path);
                Nether_Entered_Count++;
            }
            case("world_the_end") -> {
//                int value = config.getInt("Player.End-Entered-Count");
//                config.set("Player.End-Entered-Count", value + 1 );
//                config.save(path);
                End_Entered_Count++;
            }
        }
    }

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Player)){
//            int value = config.getInt("Entity.Entity-Died-Amount");
//            config.set("Entity.Entity-Died-Amount", value + 1 );
//            config.save(path);
            Entity_Died_Amount++;
        }
    }

    @EventHandler
    public void NewChunkLoadEvent(ChunkLoadEvent event) {
        if(event.isNewChunk()) {
//            int value = config.getInt("Chunks.New-Chunks-Loaded");
//            config.set("Chunks.New-Chunks-Loaded", value + 1);
//            config.save(path);
            New_Chunks_Loaded++;
        }
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerAdvancementDoneEvent event) {
//        int value = config.getInt("Player.Advancements-Done");
//        config.set("Player.Advancements-Done", value + 1);
//        config.save(path);
        Advancements_Done++;
    }

    @Override
    public void run() {
        try {
            config.set("Connection.Player-Connects", Player_Connects);
            config.set("Connection.Player-Disconnects", Player_Disconnects);

            config.set("Health.Health-Lost", Health_Lost);
            config.set("Health.Health-Regenerated", Health_Regenerated);

            config.set("Blocks.Blocks-Broken", Blocks_Broken);
            config.set("Blocks.Blocks-Built", Blocks_Built);

            config.set("Chat.Messages-Sent", Messages_Sent);
            config.set("Chat.Commands-Executed", Commands_Executed);

            config.set("Player.Player-Deaths", Player_Deaths);
            config.set("Player.Experience-Received", Experience_Received);
            config.set("Player.Advancements-Done", Advancements_Done);
            config.set("Player.Overworld-Entered-Count", Overworld_Entered_Count);
            config.set("Player.Nether-Entered-Count", Nether_Entered_Count);
            config.set("Player.End-Entered-Count", End_Entered_Count);

            config.set("Entity.Entities-Spawned", Entities_Spawned);
            config.set("Entity.Entity-Died-Amount", Entity_Died_Amount);

            config.set("Chunks.New-Chunks-Loaded", New_Chunks_Loaded);

            config.save(path);
        } catch (IOException exception) {
            LoggerInstance.severe("BukkitRunnable ERROR while saving analytics config! " + exception.getMessage());
        }
        LoggerInstance.info("SAVED ANALYTICS!");
    }
    // Runnable executed every 5 minutes: (Or at least it should!)
    //        }.runTaskTimerAsynchronously(MCL_Overseer.getPlugin(MCL_Overseer.class), 0, 6000);
}
