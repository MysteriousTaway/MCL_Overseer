package me.Taway.MCL_Overseer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Set;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class ConfigManager {

    private static final Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static FileConfiguration config = plugin.getConfig();
    //Nether:
    protected static int NetherPVE_X;
    protected static int NetherPVE_Z;
    // Overworld:
    protected static int OverworldPVE_X;
    protected static int OverworldPVE_Z;
    // End
    protected static boolean Allow_End;
    //Honor manager data:
    protected static boolean AllowHonor;
    protected static int MaxHonor;
    protected static int HonorForPlaying;
    protected static boolean CheckForAfk;
    //Penalties:
    protected static int EndEntryPenalty;
    protected static int EntitySpawnPenalty;
    protected static int BlockPlacementPenalty;
    //Statistics:
    protected static int SaveFileIntervalTicks;
    protected static int RunTimedChecksTickInterval;
    //Difficulty:
    protected static int Difficulty_DropArmorOnDeathChance;
    protected static int Difficulty_EquipmentChance;

    protected static void ReadFromConfig() {
        try {
            NetherPVE_X = config.getInt("PvE.Nether.X");
            NetherPVE_Z = config.getInt("PvE.Nether.Z");

            OverworldPVE_X = config.getInt("PvE.Overworld.X");
            OverworldPVE_Z = config.getInt("PvE.Overworld.Z");

            Allow_End = config.getBoolean("Allow-End");
            //Honor score config stuff:
            AllowHonor = config.getBoolean("Honor-Score.Enable");
            CheckForAfk = config.getBoolean("Honor-Score.Check-For-Afk");
            MaxHonor = config.getInt("Honor-Score.Max-Amount");
            //Rewards:
            HonorForPlaying = config.getInt("Honor-Score.Reward-For-Playing");
            //Penalties:
            EndEntryPenalty = config.getInt("Honor-Score.End-Entry-Penalty");
            EntitySpawnPenalty = config.getInt("Honor-Score.Entity-Spawn-Penalty");
            BlockPlacementPenalty = config.getInt("Honor-Score.Block-Placement-Penalty");
            //Statistics:
            SaveFileIntervalTicks = config.getInt("Statistics.Save-File-Interval-Ticks");
            RunTimedChecksTickInterval = config.getInt("Statistics.Run-Timed-Checks-Tick-Interval");
            //Difficulty:;
            Difficulty_DropArmorOnDeathChance = config.getInt("Difficulty.DropArmorOnDeathChance");
            Difficulty_EquipmentChance = config.getInt("Difficulty.EquipmentChance");

            PrintConfig();
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while loading config!";
            String method = "ConfigManager.ReadFromConfig";
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    protected static void PrintConfig() {
        try {
            LoggerInstance.warning("<[CONFIG]>");
            Set<String> KeySet = config.getKeys(true);
            for (String key : KeySet) {
                if (key.contains(".")) {
                    LoggerInstance.info(key + "  =>  " + config.get(key));
                }
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to print config to console (HOW!? WHY?! HOW CAN THIS FUCKING FAIL?!)! ";
            String method = "ConfigManager.PrintConfig";
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }
}
