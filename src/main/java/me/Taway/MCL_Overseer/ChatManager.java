package me.Taway.MCL_Overseer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatManager implements Listener {

    @EventHandler
    @Deprecated
    protected void onPlayerChat(PlayerChatEvent event) {
        try {
            Player sender = event.getPlayer();
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >" + " PLAYER: " + sender.getDisplayName() + " TEXT: " + event.getMessage();
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("ChatLog/" + fileName + ".txt", message);
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to log chat message event! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    @EventHandler
    protected void onCommandExecution(PlayerCommandPreprocessEvent event) {
        try {
            Player sender = event.getPlayer();
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >" + " PLAYER: " + sender.getDisplayName() + " COMMAND: " + event.getMessage();
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("CommandLog/" + fileName + ".txt", message);
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to log command event! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }
}
