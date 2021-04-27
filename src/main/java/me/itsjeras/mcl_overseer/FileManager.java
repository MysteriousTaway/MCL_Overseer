package me.itsjeras.mcl_overseer;

import org.bukkit.ChatColor;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Formatter;

public class FileManager {

    static boolean CreateFile = true;
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
            System.err.println(ChatColor.RED + "IOException: " + ioe.getMessage());
            if(CreateFile) {
                System.out.println(ChatColor.YELLOW + "<[!!!]> Creating a new file as an attempt to fix the exception!");
                CreateFile = false;
                try {
                    Formatter formatter = new Formatter("plugins/MCL_Overseer/" + filename);
                    formatter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(ChatColor.YELLOW + "<[!!!]> Creating a new file did not fix the exception!");
                CreateFile = true;
            }
        }
    }
}
