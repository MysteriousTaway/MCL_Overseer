package me.Taway.MCL_Overseer;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public final class MCL_Overseer extends JavaPlugin {

    protected File config = new File(getDataFolder(), "config.yml");
    protected static Server ServerInstance;
    protected static BukkitScheduler SchedulerInstance;
    protected static MCL_Overseer PluginInstance;
    protected static java.util.logging.Logger LoggerInstance = java.util.logging.Logger.getLogger("<OVERSEER>");

    private static final boolean FeatureTestRun = false;

    @Override
    public void onEnable() {
//         Retarded server pre-startup logic that I totally understand:
        ServerInstance = getServer();
        SchedulerInstance = ServerInstance.getScheduler();
        PluginInstance = getPlugin(getClass());

//         Plugin startup logic
//         OVERSEER LOGO:
        LoggerInstance.info("\n                ..,,;;;;;;,,,,\n[O]      .,;'';;,..,;;;,,,,,.''';;,..\n[V]    ,,''                    '';;;;,;''\n[E]   ;'    ,;@@;'  ,@@;, @@, ';;;@@;,;';.\n[R]  ''  ,;@@@@@'  ;@@@@; ''    ;;@@@@@;;;;\n[S]     ;;@@@@@;    '''     .,,;;;@@@@@@@;;;\n[E]    ;;@@@@@@;           , ';;;@@@@@@@@;;;.\n[E]     '';@@@@@,.  ,   .   ',;;;@@@@@@;;;;;;\n[R]       .   '';;;;;;;;;,;;;;@@@@@;;' ,.:;'\n             ''..,,     ''''    '  .,;'\n                 ''''''::''''''''\n");
        PluginManager manager = getServer().getPluginManager();

//         Config stuff:
        if (!config.exists()) {
//            Make:
            LoggerInstance.info("config.yml not found. Created a default config.");
            saveDefaultConfig();
        }
//         Load config:
        LoggerInstance.info("Loading config values:");
        ConfigManager.ReadFromConfig();
//         Make dirs if not found:
        FileManager.CheckForFolders();
//         Make files if not found:
        FileManager.CheckForFiles();
//         Listeners:
        try {
            manager.registerEvents(new ChatManager(), this);
            manager.registerEvents(new PlayerManager(), this);
            manager.registerEvents(new BlockManager(), this);
            manager.registerEvents(new EntityManager(), this);
            manager.registerEvents(new StatisticsHandler(), this);
            manager.registerEvents(new DifficultyHandler(), this);
        } catch (Exception exception) {
            LoggerInstance.severe("Event register exception! " + exception.getMessage());
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> onEnable Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Exception message: \n" + exception.getMessage();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }

//         Statistics:
        StatisticsManager.CheckForDataFile();
        StatisticsManager.SetConfigFile();

//         Make scheduler:
        try {
//             Repeating statistics calculations:
            BukkitTask TimedStatisticsCalculation = new StatisticsHandler().runTask(PluginInstance);
            SchedulerInstance.scheduleSyncRepeatingTask(PluginInstance, (Runnable) TimedStatisticsCalculation, 0, ConfigManager.RunTimedChecksTickInterval);
        } catch (Exception exception) {
            LoggerInstance.severe(" onEnable scheduler exception! " + exception.getMessage());
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> onEnable Exception (Statistics scheduler)<] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Exception Message: \n" + exception.getMessage();
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }
//        ! TEST thingy:
        if(FeatureTestRun) {
            String fileName = Get.CurrentDate().replace("/", "_");
            String message = "[> FEATURE TEST RUN! <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Exception message: " + "TEST MESSAGE!";
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        StatisticsManager.SaveStatistics();
        getServer().broadcastMessage(ChatColor.RED + "<[!!!]> " + ChatColor.WHITE + "Overseer plugin has been " + ChatColor.RED + "DEACTIVATED.");
    }
}
