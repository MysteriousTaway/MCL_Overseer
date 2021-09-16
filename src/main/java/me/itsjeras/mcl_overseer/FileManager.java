package me.itsjeras.mcl_overseer;

import org.bukkit.ChatColor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Formatter;

import static me.itsjeras.mcl_overseer.MCL_Overseer.LoggerInstance;

public class FileManager {

    private static boolean CreateFile = true;
    public static void writeToFile(String filename, String text) {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("plugins/MCL_Overseer/" + filename, true));
            writer.append(text);
            writer.newLine();
            writer.close();
        }
        catch(Exception exception)
        {
            LoggerInstance.severe("Exception: " + exception.getMessage());
            if(CreateFile) {
                LoggerInstance.severe("<[!!!]> Creating a new file as an attempt to fix the exception!");
                CreateFile = false;
                try {
                    Formatter formatter = new Formatter("plugins/MCL_Overseer/" + filename);
                    formatter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                LoggerInstance.severe("<[!!!]> Creating a new file did not fix the exception!");
                CreateFile = true;
            }
        }
    }

    public static void CheckForFolders() {
        // Files:
        File[] Files = {
                new File("plugins/MCL_Overseer/ActivityLog/"),
                new File("plugins/MCL_Overseer/ChatLog/"),
                new File("plugins/MCL_Overseer/CombatLog/"),
                new File("plugins/MCL_Overseer/CommandLog/"),
                new File("plugins/MCL_Overseer/DeathLog/"),
                new File("plugins/MCL_Overseer/ExceptionLog/"),
                new File("plugins/MCL_Overseer/ForbiddenActivityLog/"),
                new File("plugins/MCL_Overseer/PlayerData/"),
                new File("plugins/MCL_Overseer/Analytics/"),

        };
        try {
//            if(!Files[0].exists()) {
//                for(File file : Files) {
//                    file.mkdir();
//                }
//            }
            for (File file : Files) {
                if (!file.exists()) {
                    file.mkdir();
                    LoggerInstance.warning("CREATED FILE! " + file.getPath());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
