package me.Taway.MCL_Overseer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Formatter;

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
            MCL_Overseer.LoggerInstance.severe("Exception: " + exception.getMessage());
            if(CreateFile) {
                MCL_Overseer.LoggerInstance.severe("<[!!!]> Creating a new file as an attempt to fix the exception!");
                CreateFile = false;
                try {
                    Formatter formatter = new Formatter("plugins/MCL_Overseer/" + filename);
                    formatter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                MCL_Overseer.LoggerInstance.severe("<[!!!]> Creating a new file did not fix the exception!");
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
                new File("plugins/MCL_Overseer/_Statistics/"),
                new File("plugins/MCL_Overseer/_Analytics/"),

        };
        try {
            for (File file : Files) {
                if (!file.exists()) {
                    file.mkdir();
                    MCL_Overseer.LoggerInstance.warning("CREATED FILE! [" + file.getPath() + "]");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
