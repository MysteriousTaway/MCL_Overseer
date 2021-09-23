package me.Taway.MCL_Overseer;

import java.io.*;

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

    protected static String[] readFromFile(String filename) {
        String file = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            file = sb.toString();
            br.close();
        } catch(Exception exception) {
            String message = "[> readFromFile <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >";
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n");
        }

        if (file.length() > 0 ) {
            return file.split("\n");
        } else {
            return null;
        }
    }

    protected static void CheckForFolders() {
        //  Files:
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
                new File("plugins/MCL_Overseer/Files/"),
        };
        try {
            for (File file : Files) {
                if (!file.exists()) {
                    file.mkdir();
                    LoggerInstance.warning("CREATED FOLDER! [" + file.getPath() + "]");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected static void CheckForFiles() {
        // Files:
        File[] Files = {
                new File("plugins/MCL_Overseer/Files/WelcomeMessage.txt"),
        };
        try {
            for (File file : Files) {
                if (!file.exists()) {
                    file.createNewFile();
                    LoggerInstance.warning("CREATED FILE! [" + file.getPath() + "]");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
