package me.Taway.MCL_Overseer;

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

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class Statistics extends BukkitRunnable implements Listener {

    static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static String path;
    static FileConfiguration config;

    public static int Player_Connects;
    public static int Player_Disconnects;

    public static double Health_Lost;
    public static double Health_Regenerated;

    public static int Blocks_Broken;
    public static int Blocks_Built;

    public static int Messages_Sent;
    public static int Commands_Executed;

    public static int Player_Deaths;
    public static int Experience_Received;
    public static int Advancements_Done;
    public static int Overworld_Entered_Count;
    public static int Nether_Entered_Count;
    public static int End_Entered_Count;

    public static int Entities_Spawned;
    public static int Entity_Died_Amount;

    public static int New_Chunks_Loaded;

    public static void SetConfigFile() {
        File DataFile = new File(plugin.getDataFolder() , "/_Statistics/" + Get.CurrentDate().replace("/", "_") + ".yml");
        if (DataFile.exists()) {
            path = "plugins/MCL_Overseer/_Statistics/" + Get.CurrentDate().replace("/", "_") + ".yml";
            config = YamlConfiguration.loadConfiguration(DataFile);
        } else {
            String fileName = Get.CurrentDate().replace("/", "_");
            String message = "[> SetConfigFile Exception (ANALYTICS) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);

            CheckForDataFile();
        }
    }


    public static void CheckForDataFile(){
        File file = new File("plugins/MCL_Overseer/_Statistics/", Get.CurrentDate().replace("/", "_") + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
                FileManager.writeToFile("/_Statistics/" + Get.CurrentDate().replace("/", "_") + ".yml",
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
            Health_Lost += event.getDamage();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player_Deaths++;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player_Connects++;
    }

    @EventHandler
    public void onPlayerDisconnectEvent(PlayerQuitEvent event) {
        Player_Disconnects++;
    }

    @EventHandler @Deprecated
    public void onPlayerChat(PlayerChatEvent event) {
        Messages_Sent++;
    }

    @EventHandler
    public void onCommandExecution(PlayerCommandPreprocessEvent event) {
        Commands_Executed++;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entities_Spawned++;
    }

    @EventHandler
    public void EntityRegainHealthEvent(EntityRegainHealthEvent event) {
        if(event.getEntity() instanceof Player ) {
            Health_Regenerated += event.getAmount();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Blocks_Built++;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Blocks_Broken++;
    }

    @EventHandler
    public void PlayerExpChangeEvent(PlayerExpChangeEvent event) {
        Experience_Received += event.getAmount();
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) {
        switch(event.getTo().getWorld().getName()) {
            case("world") -> {
                Overworld_Entered_Count++;
            }
            case("world_nether") -> {
                Nether_Entered_Count++;
            }
            case("world_the_end") -> {
                End_Entered_Count++;
            }
        }
    }

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Player)){
            Entity_Died_Amount++;
        }
    }

    @EventHandler
    public void NewChunkLoadEvent(ChunkLoadEvent event) {
        if(event.isNewChunk()) {
            New_Chunks_Loaded++;
        }
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerAdvancementDoneEvent event) {
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

    public static void SaveStatistics() {
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

            LoggerInstance.info("Saved statistics!");
        } catch (IOException exception) {
            LoggerInstance.severe("BukkitRunnable ERROR while saving analytics config! " + exception.getMessage());
        }

    }
}
