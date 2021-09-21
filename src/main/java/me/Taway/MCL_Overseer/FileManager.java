package me.Taway.MCL_Overseer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class FileManager {

    protected static void writeToFile(String filename, String text) {
        try {
            File file = new File("plugins/MCL_Overseer/" + filename);
            if (!file.exists()) {
                LoggerInstance.severe("File not found! [" + file.getPath() + "]");
                file.createNewFile();
                LoggerInstance.warning("Created file! [" + file.getPath() + "]");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(text);
            writer.newLine();
            writer.close();
        } catch (Exception exception) {
            LoggerInstance.severe("Exception: " + exception.getMessage());
            LoggerInstance.severe("!!!! THIS ERROR CANNOT BE LOGGED TO EXCEPTION LOG FILE !!!!");
        }
    }

    protected static void CheckForFolders() {
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
                    LoggerInstance.warning("CREATED FILE! [" + file.getPath() + "]");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
