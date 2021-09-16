package me.itsjeras.mcl_overseer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
                        "Player-Stats:\n" +
                        "# Connect\n" +
                        "  Player-Connects: 0\n" +
                        "  Player-Disconnects: 0\n" +
                        "  Player-Handshakes: 0\n" +
                        "# Health:\n" +
                        "  Health-Lost: 0\n" +
                        "  Health-Regenerated: 0\n" +
                        "# Blocks:\n" +
                        "  Blocks-Broken: 0\n" +
                        "  Blocks-Built: 0\n");
//                FileManager.writeToFile("PlayerData/" + Get.CurrentDate().replace("/", "_") + ".yml", "# File created on: " + Get.CurrentDate() + "\n" + Get.CurrentDate().replace("/", "_") + ":\n   Username: " + player.getDisplayName() + "\n   Honor: 100" + "\n   IP: " + player.getAddress().getHostName());
            } catch (IOException exception) {
                String fileName = Get.CurrentDate().replace("/", "_");
                String message = "[> CheckForDataFile Exception (ANALYTICS)<] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Exception message: " + exception.getMessage();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            }
        } else {
//            SetIP(player);
        }
    }
}
