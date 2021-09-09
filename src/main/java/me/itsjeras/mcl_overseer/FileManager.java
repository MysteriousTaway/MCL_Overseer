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
        catch(Exception ioe)
        {
            LoggerInstance.info(ChatColor.RED + "IOException: " + ioe.getMessage());
            if(CreateFile) {
                LoggerInstance.info(ChatColor.YELLOW + "<[!!!]> Creating a new file as an attempt to fix the exception!");
                CreateFile = false;
                try {
                    Formatter formatter = new Formatter("plugins/MCL_Overseer/" + filename);
                    formatter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                LoggerInstance.info(ChatColor.YELLOW + "<[!!!]> Creating a new file did not fix the exception!");
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

        };
        try {
            if(!Files[0].exists()) {
                for(File file : Files) {
                    file.mkdir();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
