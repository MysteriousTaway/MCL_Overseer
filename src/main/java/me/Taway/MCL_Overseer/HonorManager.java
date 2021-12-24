package me.Taway.MCL_Overseer;

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

    protected static void ChangeHonorValueOfPlayer(Player player, int value) {
        try {
            if (ConfigManager.AllowHonor) {
                // Load config to
                SetConfigFile(player.getUniqueId());
                //get honor
                int honor = config.getInt(player.getUniqueId() + ".Honor");
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
                    player.sendMessage("<[!!]> Your Honor has changed! (" + value + ") your Honor is now: " + honor + "/" + ConfigManager.MaxHonor);
                    // Check if honor is less than 0:
                    if (honor < 0) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getDisplayName(), "You broke rules of PvE too many times. If you think this was a mistake contact a moderator on Discord.\nThis action was performed automatically by " + ChatColor.RED + "<[OVERSEER]>", null, "<[OVERSEER]>");
                        player.kickPlayer("You broke rules of PvE too many times. If you think this was a mistake contact a moderator on Discord.\nThis action was performed automatically by " + ChatColor.RED + "<[OVERSEER]>");
                    }
                } else {
                    getLogger().warning("HonorManager.ChangeHonorValueOfPlayer value is equal to zero or player is equal to null.");
                }
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to change honor of player! ";
            String method = "HonorManager.ChangeHonorValueOfPlayer";
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    private static void SetConfigFile(UUID UUID) {
        File DataFile = new File(plugin.getDataFolder(), "/PlayerData/" + UUID.toString() + ".yml");
        if (DataFile.exists()) {
            path = "plugins/MCL_Overseer/PlayerData/" + UUID + ".yml";
            config = YamlConfiguration.loadConfiguration(DataFile);
        } else {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to SetConfigFile! ";
            String method = "HonorManager.SetConfigFile";
            message = "[" + method + "]" + message;
            FileManager.LogException(message);
        }
    }

    protected static void SetIP(Player player) {
        try {
        SetConfigFile(player.getUniqueId());
        config.set(player.getUniqueId() + ".IP", player.getAddress().getHostName());
            config.save(path);
        } catch (IOException exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to set IP! ";
            String method = "HonorManager.SetIP";
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    protected static void CheckForDataFile(Player player) {
        try {
            UUID UUID = player.getUniqueId();
            File file = new File("plugins/MCL_Overseer/PlayerData/", UUID + ".yml");
            if (!file.exists()) {
                FileManager.writeToFile("PlayerData/" + UUID + ".yml", "# File created on: " + Get.CurrentDate() + "\n" + UUID + ":\n   Username: " + player.getDisplayName() + "\n   Honor: 100" + "\n   IP: " + player.getAddress().getHostName());
            } else {
                SetIP(player);
            }
        }catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to check for file! ";
            String method = "HonorManager.CheckForDataFile";
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }
}