package me.itsjeras.mcl_overseer;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class MCL_Overseer extends JavaPlugin {

    public File config = new File(getDataFolder(), "config.yml");

    // Update because PaperMC wants me to do this:
    // Public instances:
    public static Server ServerInstance;
    public static Logger LoggerInstance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        //OVERSEER LOGO:
        LoggerInstance.info("                ..,,;;;;;;,,,,\n[O]      .,;'';;,..,;;;,,,,,.''';;,..\n[V]    ,,''                    '';;;;,;''\n[E]   ;'    ,;@@;'  ,@@;, @@, ';;;@@;,;';.\n[R]  ''  ,;@@@@@'  ;@@@@; ''    ;;@@@@@;;;;\n[S]     ;;@@@@@;    '''     .,,;;;@@@@@@@;;;\n[E]    ;;@@@@@@;           , ';;;@@@@@@@@;;;.\n[E]     '';@@@@@,.  ,   .   ',;;;@@@@@@;;;;;;\n[R]       .   '';;;;;;;;;,;;;;@@@@@;;' ,.:;'\n             ''..,,     ''''    '  .,;'\n                 ''''''::''''''''\n");
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
            manager.registerEvents(new ChatManager(), this);
            manager.registerEvents(new PlayerManager(), this);
            manager.registerEvents(new BlockManager(), this);
            manager.registerEvents(new EntityManager(), this);
        } catch (Exception exception) {
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> onEnable Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >";
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt","\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }
        //Commands:
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().broadcastMessage(ChatColor.RED + "<[!!!]> "+ ChatColor.WHITE + "Overseer plugin has been " + ChatColor.RED + "DEACTIVATED.");
    }
}
