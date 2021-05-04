package me.itsjeras.mcl_overseer;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class HonorManager {

    static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static String path;
    static FileConfiguration config;

    public static void ChangeHonorValueOfPlayer (Player player, int value) {
        if(ConfigManager.AllowHonor) {
            // Load config to
            SetConfigFile(player.getUniqueId());
            //get honor
            int honor = config.getInt(player.getUniqueId() + ".Honor");
            try {
                if (player != null && value != 0) {
                    // Add value to honor. Even if value is negative this code will work.
                    honor += value;
                    // Check if value is more than max:
                    if (honor > ConfigManager.MaxHonor) {
                        honor = ConfigManager.MaxHonor;
                    }
                    // Write value to data file:
                    String valuePath = player.getUniqueId() + ".Honor";
                    config.set(valuePath, honor);
                    config.save(path);
                    // Inform player of honor change:
                    player.sendMessage("<[!!]> Your Honor has changed! (" + value  + ") your Honor is now: " + honor + "/" + ConfigManager.MaxHonor);
                    // Check if honor is less than 0:
                    if (honor < 0) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getDisplayName(), "You broke rules of PvE too many times. If you think this was a mistake contact a moderator on Discord.\nThis action was performed automatically by " + ChatColor.RED + "<[OVERSEER]>", null, "<[OVERSEER]>");
                        player.kickPlayer("You broke rules of PvE too many times. If you think this was a mistake contact a moderator on Discord.\nThis action was performed automatically by " + ChatColor.RED + "<[OVERSEER]>");
                    }
                } else {
                    getLogger().warning("HonorManager.ChangeHonorValueOfPlayer value is equal to zero or player is equal to null.");
                }
            } catch (Exception exception) {
                String fileName = Get.CurrentDate().replace("/", "_");
                String message = "[> ChangeHonorOfPlayer Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Player:" + player.getDisplayName();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
            }
        }
    }

    private static void SetConfigFile(UUID UUID) {
        File DataFile = new File(plugin.getDataFolder() , "/PlayerData/" + UUID.toString() + ".yml");
        if (DataFile.exists()) {
            path = "plugins/MCL_Overseer/PlayerData/" + UUID + ".yml";
            config = YamlConfiguration.loadConfiguration(DataFile);
        } else {
            String fileName = Get.CurrentDate().replace("/", "_");
            String message = "[> SetConfigFile Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " UUID:" + UUID;
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
        }
    }

    public static void SetIP(Player player) {
        SetConfigFile(player.getUniqueId());
        config.set(player.getUniqueId() + ".IP", player.getAddress().getHostName());
        try {
            config.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CheckForDataFile(Player player){
        UUID UUID = player.getUniqueId();
        File file = new File("plugins/MCL_Overseer/PlayerData/", UUID + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
                FileManager.writeToFile("PlayerData/" + UUID + ".yml", "# File created on: " + Get.CurrentDate() + "\n" + UUID + ":\n   Username: " + player.getDisplayName() + "\n   Honor: 100" + "\n   IP: " + player.getAddress().getHostName());
            } catch (IOException exception) {
                String fileName = Get.CurrentDate().replace("/", "_");
                String message = "[> CheckForDataFile Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " Exception message: " + exception.getMessage();
                FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            }
        } else {
            SetIP(player);
        }
    }
}