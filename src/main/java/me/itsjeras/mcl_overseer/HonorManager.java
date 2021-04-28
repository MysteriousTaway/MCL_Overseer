package me.itsjeras.mcl_overseer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class HonorManager {

    private static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static FileConfiguration config;

    public static void AddHonor(Player player, int amount) {

    }

    public static void SubtractHonor(Player player, int amount) {

    }

    private static void GetDataFile(UUID UUID){
        File file = new File("plugins/MCL_Overseer/PlayerData/", UUID + ".yml");

    }

    public static void CheckForDataFile(Player player){
        UUID UUID = player.getUniqueId();
        File file = new File("plugins/MCL_Overseer/PlayerData/", UUID + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
                FileManager.writeToFile("PlayerData/" + UUID + ".yml", "# File created on: " + Get.CurrentDate() + "\nUUID: " + UUID + "\nUsername: " + player.getDisplayName() + "\nHonor: 100" + "\nIP: " + player.getAddress().getHostName());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
