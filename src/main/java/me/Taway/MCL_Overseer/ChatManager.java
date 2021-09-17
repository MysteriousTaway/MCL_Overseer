package me.Taway.MCL_Overseer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatManager implements Listener {

    @EventHandler @Deprecated
    public void onPlayerChat(PlayerChatEvent event) {
        Player sender = event.getPlayer();
        String message = "<DATE: " +Get.CurrentDate()+ " TIME: " + Get.CurrentTime() + " >" +" PLAYER: " + sender.getDisplayName() + " TEXT: " + event.getMessage();
        String fileName = Get.CurrentDate().replace("/","_");
        FileManager.writeToFile("ChatLog/" + fileName + ".txt", message);
    }

    @EventHandler
    public void onCommandExecution(PlayerCommandPreprocessEvent event) {
        Player sender = event.getPlayer();
        String message = "<DATE: " +Get.CurrentDate()+ " TIME: " + Get.CurrentTime() + " >" +" PLAYER: " + sender.getDisplayName() + " COMMAND: " + event.getMessage();
        String fileName = Get.CurrentDate().replace("/","_");
        FileManager.writeToFile("CommandLog/" + fileName + ".txt", message);
    }
}
