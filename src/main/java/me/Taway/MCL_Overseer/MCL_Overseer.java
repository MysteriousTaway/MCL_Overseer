package me.Taway.MCL_Overseer;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public final class MCL_Overseer extends JavaPlugin {

    public File config = new File(getDataFolder(), "config.yml");

    // Update because PaperMC wants me to do this:
    // Public instances:
    public static Server ServerInstance;
    public static BukkitScheduler SchedulerInstance;
    public static MCL_Overseer PluginInstance;
    public static java.util.logging.Logger LoggerInstance = java.util.logging.Logger.getLogger("<OVERSEER>");

    @Override
    public void onEnable() {
        // Retarded server startup logic that i don't understand:
        ServerInstance = getServer();
        SchedulerInstance = ServerInstance.getScheduler();
        PluginInstance = getPlugin(getClass());
        // Plugin startup logic
        //OVERSEER LOGO:
        LoggerInstance.info("\n                ..,,;;;;;;,,,,\n[O]      .,;'';;,..,;;;,,,,,.''';;,..\n[V]    ,,''                    '';;;;,;''\n[E]   ;'    ,;@@;'  ,@@;, @@, ';;;@@;,;';.\n[R]  ''  ,;@@@@@'  ;@@@@; ''    ;;@@@@@;;;;\n[S]     ;;@@@@@;    '''     .,,;;;@@@@@@@;;;\n[E]    ;;@@@@@@;           , ';;;@@@@@@@@;;;.\n[E]     '';@@@@@,.  ,   .   ',;;;@@@@@@;;;;;;\n[R]       .   '';;;;;;;;;,;;;;@@@@@;;' ,.:;'\n             ''..,,     ''''    '  .,;'\n                 ''''''::''''''''\n");
        PluginManager manager = getServer().getPluginManager();

        // Config stuff:
        if (!config.exists()) {
            //Make:
            LoggerInstance.info("config.yml not found. Created a default config.");
            saveDefaultConfig();
        }
        // Load config:
        LoggerInstance.info("Loading config values:");
        ConfigManager.ReadFromConfig();
        // Make dirs if not found:
        FileManager.CheckForFolders();
        // Listeners:
        try {
            // Version 0.1
            manager.registerEvents(new ChatManager(), this);
            manager.registerEvents(new PlayerManager(), this);
            manager.registerEvents(new BlockManager(), this);
            manager.registerEvents(new EntityManager(), this);
            // NEW! in version 0.3
            manager.registerEvents(new Statistics(), this);
        } catch (Exception exception) {
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> onEnable Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >";
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }

        // Set all statistics to 0
        NullStats();

        // Statistics:
        Statistics.CheckForDataFile();
        Statistics.SetConfigFile();

        // NEW! Update 0.3.5
        // Make scheduler:
        try {
            BukkitTask SaveAnalytics = new Statistics().runTask(PluginInstance);
            SchedulerInstance.scheduleSyncRepeatingTask(PluginInstance, (Runnable) SaveAnalytics, 0, ConfigManager.SaveFileIntervalTicks);
        } catch (Exception exception) {
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> onEnable Exception (Statistics scheduler)<] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >";
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }
        //Commands:
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Statistics.SaveStatistics();
        getServer().broadcastMessage(ChatColor.RED + "<[!!!]> " + ChatColor.WHITE + "Overseer plugin has been " + ChatColor.RED + "DEACTIVATED.");
    }

    private static void NullStats() {
        Statistics.Player_Connects = 0;
        Statistics.Player_Disconnects = 0;
        Statistics.Health_Lost = 0;
        Statistics.Health_Regenerated = 0;
        Statistics.Blocks_Broken = 0;
        Statistics.Blocks_Built = 0;
        Statistics.Messages_Sent = 0;
        Statistics.Commands_Executed = 0;
        Statistics.Player_Deaths = 0;
        Statistics.Experience_Received = 0;
        Statistics.Advancements_Done = 0;
        Statistics.Overworld_Entered_Count = 0;
        Statistics.Nether_Entered_Count = 0;
        Statistics.End_Entered_Count = 0;
        Statistics.Entities_Spawned = 0;
        Statistics.Entity_Died_Amount = 0;
        Statistics.New_Chunks_Loaded = 0;
    }
}