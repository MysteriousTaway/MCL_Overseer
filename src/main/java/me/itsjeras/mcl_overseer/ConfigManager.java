package me.itsjeras.mcl_overseer;

import org.bukkit.plugin.Plugin;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static Plugin plugin = MCL_Overseer.getPlugin(MCL_Overseer.class);
    static FileConfiguration config = plugin.getConfig();
    //Nether:
    public static int NetherPVE_X;
    public static int NetherPVE_Z;
    // Overworld:
    public static int OverworldPVE_X;
    public static int OverworldPVE_Z;
    // End
    public static boolean Allow_End;
    public static void ReadFromConfig(){
        try {
            NetherPVE_X = config.getInt("PvE.Nether.X");
            NetherPVE_Z = config.getInt("PvE.Nether.Z");

            OverworldPVE_X = config.getInt("PvE.Overworld.X");
            OverworldPVE_Z = config.getInt("PvE.Overworld.Z");

            Allow_End = config.getBoolean("Allow-End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteToConfig() {

    }

    public static void reloadValues(){

    }
}
