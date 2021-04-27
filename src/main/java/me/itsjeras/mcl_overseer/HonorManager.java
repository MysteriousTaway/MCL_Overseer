package me.itsjeras.mcl_overseer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class HonorManager {

    private static Plugin plugin;
    private static File honorFile = new File(plugin.getDataFolder(), "overseer/PlayerData/reputation.yml");

    public static void AddHonor(Player player, int amount) {
        if(!honorFile.exists()){
            plugin.saveResource("overseer/PlayerData/reputation.yml",false);
        }

    }

    public static void SubtractHonor(Player player, int amount) {
        if(!honorFile.exists()){
            plugin.saveResource("overseer/PlayerData/reputation.yml",false);
        }

    }
}
