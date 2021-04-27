package me.itsjeras.mcl_overseer;

import org.bukkit.plugin.Plugin;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static Plugin plugin;
    static FileConfiguration config = plugin.getConfig();

    //Nether:
    public static int NetherPVE_X;
    public static int NetherPVE_Z;
    // Overworld:
    public static int OverworldPVE_X;
    public static int OverworldPVE_Z;

    public static void ReadFromConfig(){
        try {
            if (config.contains("PvE.Nether.X")) {
                System.out.println("   contains x");
            } else {
                System.out.println(" ! contains x");
            }
            NetherPVE_X = config.getInt("PvE.Nether.X");
            NetherPVE_Z = config.getInt("PvE.Nether.Z");

            OverworldPVE_X = config.getInt("PvE.Overworld.X");
            OverworldPVE_Z = config.getInt("PvE.Overworld.Z");
            System.out.println(NetherPVE_X + " " + NetherPVE_Z + " " + OverworldPVE_X + " " + OverworldPVE_Z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteToConfig() {

    }

    public static void reloadValues(){

    }
}
