package me.itsjeras.mcl_overseer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
                        "# Connection:\n"+
                                "Player-Connects: 0\n"+
                                "Player-Disconnects: 0\n"+
                                "# Health:\n"+
                                "Health-Lost: 0\n"+
                                "Health-Regenerated: 0\n"+
                                "# Blocks:\n"+
                                "Blocks-Broken: 0 \n"+
                                "Blocks-Built: 0\n"+
                                "# Chat:\n"+
                                "Messages-Sent: 0\n"+
                                "Commands-Executed: 0\n"+
                                "# Spawn:\n"+
                                "Player-Deaths: 0\n"+
                                "Entities-Spawned: 0\n");
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
            double value = config.getInt("Health-Lost");
            config.set("Health-Lost", value + event.getDamage());
            config.save(path);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
        int value = config.getInt("Player-Deaths");
        config.set("Player-Deaths", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) throws IOException {
        int value = config.getInt("Player-Connects");
        config.set("Player-Connects", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onPlayerDisconnectEvent(PlayerQuitEvent event) throws IOException {
        int value = config.getInt("Player-Disconnects");
        config.set("Player-Disconnects", value + 1);
        config.save(path);
    }

    @EventHandler @Deprecated
    public void onPlayerChat(PlayerChatEvent event) throws IOException {
        int value = config.getInt("Messages-Sent");
        config.set("Messages-Sent", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onCommandExecution(PlayerCommandPreprocessEvent event) throws IOException {
        int value = config.getInt("Commands-Executed");
        config.set("Commands-Executed", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) throws IOException {
        int value = config.getInt("Entities-Spawned");
        config.set("Entities-Spawned", value + 1);
        config.save(path);
    }

    @EventHandler
    public void EntityRegainHealthEvent(EntityRegainHealthEvent event) throws IOException {
        if(event.getEntity() instanceof Player ) {
            double value = config.getDouble("Health-Regenerated");
            config.set("Health-Regenerated", value + event.getAmount());
            config.save(path);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws IOException {
        int value = config.getInt("Blocks-Built");
        config.set("Blocks-Built", value + 1);
        config.save(path);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws IOException {
        int value = config.getInt("Blocks-Broken");
        config.set("Blocks-Broken", value + 1);
        config.save(path);
    }
}
