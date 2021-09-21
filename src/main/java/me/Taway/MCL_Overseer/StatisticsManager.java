package me.Taway.MCL_Overseer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class StatisticsManager extends BukkitRunnable {

    static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static String path;
    static FileConfiguration config;

    protected static int Player_Connects;
    protected static int Player_Disconnects;

    protected static double Health_Lost;
    protected static double Health_Regenerated;

    protected static int Blocks_Broken;
    protected static int Blocks_Built;

    protected static int Messages_Sent;
    protected static int Commands_Executed;

    protected static int Player_Deaths;
    protected static int Experience_Received;
    protected static int Advancements_Done;
    protected static int Overworld_Entered_Count;
    protected static int Nether_Entered_Count;
    protected static int End_Entered_Count;

    protected static int Entities_Spawned;
    protected static int Entity_Died_Amount;

    // World quadrant statistics:
    protected static int Overworld_X_plus_Z_plus;
    protected static int Overworld_X_minus_Z_plus;
    protected static int Overworld_X_plus_Z_minus;
    protected static int Overworld_X_minus_Z_minus;

    protected static int Nether_X_plus_Z_plus;
    protected static int Nether_X_minus_Z_plus;
    protected static int Nether_X_plus_Z_minus;
    protected static int Nether_X_minus_Z_minus;

    protected static int End_X_plus_Z_plus;
    protected static int End_X_minus_Z_plus;
    protected static int End_X_plus_Z_minus;
    protected static int End_X_minus_Z_minus;
    // New chunks:
    protected static int New_Chunks_Loaded;
    protected static int Overworld_New_Chunk_Count;
    protected static int Nether_New_Chunk_Count;
    protected static int End_New_Chunk_Count;

    public static FileConfiguration getConfig() {
        return config;
    }

    protected static void SetConfigFile() {
        File DataFile = new File(plugin.getDataFolder(), "/_Statistics/" + Get.CurrentDate().replace("/", "_") + ".yml");
        if (DataFile.exists()) {
            path = "plugins/MCL_Overseer/_Statistics/" + Get.CurrentDate().replace("/", "_") + ".yml";
            config = YamlConfiguration.loadConfiguration(DataFile);

            LoadStatistics();

        } else {
            String fileName = Get.CurrentDate().replace("/", "_");
            String message = "[> SetConfigFile Exception (Statistics) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);

            CheckForDataFile();
        }
    }

    protected static void CheckForDataFile() {
        File file = new File("plugins/MCL_Overseer/_Statistics/", Get.CurrentDate().replace("/", "_") + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileManager.writeToFile("/_Statistics/" + Get.CurrentDate().replace("/", "_") + ".yml",
                        "Connection:" +
                                "\n  Player-Connects: 0" +
                                "\n  Player-Disconnects: 0" +
                                "\nHealth:" +
                                "\n  Health-Lost: 0" +
                                "\n  Health-Regenerated: 0" +
                                "\nBlocks:" +
                                "\n  Blocks-Broken: 0 " +
                                "\n  Blocks-Built: 0" +
                                "\nChat:" +
                                "\n  Messages-Sent: 0" +
                                "\n  Commands-Executed: 0" +
                                "\nPlayer:" +
                                "\n  Player-Deaths: 0" +
                                "\n  Experience-Received: 0" +
                                "\n  Advancements-Done: 0" +
                                "\n  Overworld-Entered-Count: 0" +
                                "\n  Nether-Entered-Count: 0" +
                                "\n  End-Entered-Count: 0" +
                                "\nEntity:" +
                                "\n  Entities-Spawned: 0" +
                                "\n  Entity-Died-Amount: 0" +
                                "\nChunks:" +
                                "\n  New-Chunks-Loaded: 0" +
                                "\n  Overworld-New-Chunk-Count: 0" +
                                "\n  Nether-New-Chunk-Count: 0" +
                                "\n  End-New-Chunk-Count: 0" +
                                "\nOverworld:" +
                                "\n  X-plus_Z-plus: 0" +
                                "\n  X-minus_Z-plus: 0" +
                                "\n  X-plus_Z-minus: 0" +
                                "\n  X-minus_Z-minus: 0" +
                                "\nNether:" +
                                "\n  X-plus_Z-plus: 0" +
                                "\n  X-minus_Z-plus: 0" +
                                "\n  X-plus_Z-minus: 0" +
                                "\n  X-minus_Z-minus: 0" +
                                "\nEnd:" +
                                "\n  X-plus_Z-plus: 0" +
                                "\n  X-minus_Z-plus: 0" +
                                "\n  X-plus_Z-minus: 0" +
                                "\n  X-minus_Z-minus: 0"

                );
                LoggerInstance.warning("Created new statistics file!");
            } catch (IOException exception) {
                String fileName = Get.CurrentDate().replace("/", "_");
                String message = "[> CheckForDataFile Exception (Statistics)<] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Exception message: " + exception.getMessage();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            }
        }
    }

    protected static void SaveStatistics() {
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

            config.set("Overworld.X-plus_Z-plus", Overworld_X_plus_Z_plus);
            config.set("Overworld.X-minus_Z-plus", Overworld_X_minus_Z_plus);
            config.set("Overworld.X-plus_Z-minus", Overworld_X_plus_Z_minus);
            config.set("Overworld.X-minus_Z-minus", Overworld_X_minus_Z_minus);

            config.set("Nether.X-plus_Z-plus", Nether_X_plus_Z_plus);
            config.set("Nether.X-minus_Z-plus", Nether_X_minus_Z_plus);
            config.set("Nether.X-plus_Z-minus", Nether_X_plus_Z_minus);
            config.set("Nether.X-minus_Z-minus", Nether_X_minus_Z_minus);

            config.set("End.X-plus_Z-plus", End_X_plus_Z_plus);
            config.set("End.X-minus_Z-plus", End_X_minus_Z_plus);
            config.set("End.X-plus_Z-minus", End_X_plus_Z_minus);
            config.set("End.X-minus_Z-minus", End_X_minus_Z_minus);

            config.set("Chunks.Overworld-New-Chunk-Count", Overworld_New_Chunk_Count);
            config.set("Chunks.Nether-New-Chunk-Count", Nether_New_Chunk_Count);
            config.set("Chunks.End-New-Chunk-Count", End_New_Chunk_Count);

            config.save(path);

            LoggerInstance.info("Saved statistics!");
        } catch (IOException exception) {
            LoggerInstance.severe("Exception while saving statistics config! " + exception.getMessage());
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> SaveStatistics <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Exception Message: \n" + exception.getMessage();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }

    }

    protected static void LoadStatistics() {
        try {
            // Load data:
            Player_Connects = config.getInt("Connection.Player-Connects");
            Player_Disconnects = config.getInt("Connection.Player-Disconnects");

            Health_Lost = config.getDouble("Health.Health-Lost");
            Health_Regenerated = config.getDouble("Health.Health-Regenerated");

            Blocks_Broken = config.getInt("Blocks.Blocks-Broken");
            Blocks_Built = config.getInt("Blocks.Blocks-Built");

            Messages_Sent = config.getInt("Chat.Messages-Sent");
            Commands_Executed = config.getInt("Chat.Commands-Executed");

            Player_Deaths = config.getInt("Player.Player-Deaths");
            Experience_Received = config.getInt("Player.Experience-Received");
            Advancements_Done = config.getInt("Player.Advancements-Done");
            Overworld_Entered_Count = config.getInt("Player.Overworld-Entered-Count");
            Nether_Entered_Count = config.getInt("Player.Nether-Entered-Count");
            End_Entered_Count = config.getInt("Player.End-Entered-Count");

            Entities_Spawned = config.getInt("Entity.Entities-Spawned");
            Entity_Died_Amount = config.getInt("Entity.Entity-Died-Amount");

            New_Chunks_Loaded = config.getInt("Chunks.New-Chunks-Loaded");
            Overworld_New_Chunk_Count = config.getInt("Chunks.Overworld-New-Chunk-Count");
            Nether_New_Chunk_Count = config.getInt("Chunks.Nether-New-Chunk-Count");
            End_New_Chunk_Count = config.getInt("Chunks.End-New-Chunk-Count");

            Overworld_X_plus_Z_plus = config.getInt("Overworld.X-plus_Z-plus");
            Overworld_X_minus_Z_plus = config.getInt("Overworld.X-minus_Z-plus");
            Overworld_X_plus_Z_minus = config.getInt("Overworld.X-plus_Z-minus");
            Overworld_X_minus_Z_minus = config.getInt("Overworld.X-minus_Z-minus");

            Nether_X_plus_Z_plus = config.getInt("Nether.X-plus_Z-plus");
            Nether_X_minus_Z_plus = config.getInt("Nether.X-minus_Z-plus");
            Nether_X_plus_Z_minus = config.getInt("Nether.X-plus_Z-minus");
            Nether_X_minus_Z_minus = config.getInt("Nether.X-minus_Z-minus");

            End_X_plus_Z_plus = config.getInt("End.X-plus_Z-plus");
            End_X_minus_Z_plus = config.getInt("End.X-minus_Z-plus");
            End_X_plus_Z_minus = config.getInt("End.X-plus_Z-minus");
            End_X_minus_Z_minus = config.getInt("End.X-minus_Z-minus");

            // Print to console:
            PrintStatistics();
        } catch (Exception exception) {
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> Statistics <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >";
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }
    }

    protected static void PrintStatistics() {
        LoggerInstance.warning("<[STATS]>");
        Set<String> KeySet = config.getKeys(true);
        for (String key : KeySet) {
            if(key.contains(".")) {
                LoggerInstance.info(key + "  =>  " + config.get(key));
            }
        }
    }
    @Override
    public void run() {
        try {
            SaveStatistics();
        } catch (Exception exception) {
            LoggerInstance.severe("BukkitRunnable ERROR while saving statistics config! " + exception.getMessage());
            String fileName = Get.CurrentDate().replace("/", "_");
            String message = "[> StatisticsManager (run)<] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Exception message: " + exception.getMessage();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
        }
    }
}
