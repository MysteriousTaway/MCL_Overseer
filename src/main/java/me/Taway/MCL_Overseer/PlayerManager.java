package me.Taway.MCL_Overseer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class PlayerManager implements Listener {
    @EventHandler
    protected void onPlayerDamage(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
//                Player player = ((Player) event).getPlayer();
//                Player attacker = (Player) event.getDamager();
                String player = ((Player) event.getEntity()).getPlayer().getDisplayName();
                String attacker = ((Player) event.getDamager()).getPlayer().getDisplayName();
                double distance = event.getEntity().getLocation().distance(event.getDamager().getLocation());
                if (distance < 3.6) {
                    assert player != null;
                    Location location = event.getDamager().getLocation();
                    // Location of attacked player has to be more than 8000 on x or z axis to be considered valid.
                    if (!Get.isInPvE(location)) {
                        String message = "[PvP] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >" + " ATTACKER: " + attacker + " VICTIM: " + player + " DAMAGE: " + event.getDamage();
                        String fileName = Get.CurrentDate().replace("/", "_");
                        FileManager.writeToFile("CombatLog/" + fileName + ".txt", message);
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                    event.getDamager().sendMessage(ChatColor.RED + "<[!!!]> The use of REACH hack is prohibited on this server.");
                }
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to validate player damage event! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    @EventHandler
    protected void onPlayerDeath(PlayerDeathEvent event) {
        try {
            Player deadPlayer = event.getEntity();
            Location location = event.getEntity().getLocation();
            String deathMessage = event.getDeathMessage();

            String message = "[> DEATH <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + deadPlayer.getDisplayName() + " DEATH MESSAGE: " + deathMessage + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("DeathLog/" + fileName + ".txt", message);
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to log player death! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    @EventHandler
    protected void endPortalListener(PlayerTeleportEvent event) {
        try {
            if (!ConfigManager.Allow_End) {
                if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && !event.getPlayer().isOp()) {
                    // Get data:
                    Player player = event.getPlayer();
                    Location bedLocation = player.getBedSpawnLocation();
                    assert bedLocation != null;
                    // Punishment
                    getServer().broadcastMessage(ChatColor.RED + "<[OVERSEER]> Player: " + player.getDisplayName() + " broke server rules by using end portal and was penalised.");
                    player.getInventory().clear();
                    player.setExp(0);
                    if (player.getBedSpawnLocation() != null) {
                        player.teleport(bedLocation);
                    } else {
                        player.setHealth(0);
                    }
                    Location location = event.getPlayer().getLocation();
                    World world = location.getWorld();
                    assert world != null;
                    String message = "[END PORTAL] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " WORLD: " + world.getName() + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
                    String fileName = Get.CurrentDate().replace("/", "_");
                    FileManager.writeToFile("ForbiddenActivityLog/" + fileName + ".txt", message);
                    // Punish:
                    //  Part 1:
                    HonorManager.ChangeHonorValueOfPlayer(player, ConfigManager.EndEntryPenalty);
                    //  Part 2:
                    player.kickPlayer("You were penalised for breaking server rules. Your inventory and experience was reset.\nThis action was performed automatically by " + ChatColor.RED + "<[OVERSEER]>");
                }
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to validate player teleport event! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    @EventHandler
    protected void onPlayerJoinEvent(PlayerJoinEvent event) {
        try {
            if (event != null) {
                //Create log file:
                HonorManager.CheckForDataFile(event.getPlayer());
                //Log login:
                Player player = event.getPlayer();
                Location location = event.getPlayer().getLocation();
                World world = location.getWorld();
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                String message = "[> Join <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " WORLD: " + world.getName() + " LOCATION: X=" + x + " Y=" + y + " Z=" + z + " IP: " + player.getAddress().getHostName();
                String fileName = Get.CurrentDate().replace("/", "_");
                FileManager.writeToFile("ActivityLog/" + fileName + ".txt", message);

//            Send player welcome message from the text file:
                File WelcomeFile = new File("plugins/MCL_Overseer/Files/WelcomeMessage.txt");
                String[] text = FileManager.ReadFromFile(WelcomeFile.toString());
                if (text != null) {
                    player.sendMessage(ChatColor.GOLD + "<AUTOMATED MESSAGE>\n");
                    for (String TextLine : text) {
                        player.sendMessage(ChatColor.WHITE + Get.cleanTextContent(TextLine));
                    }
                    player.sendMessage(ChatColor.GOLD + "</AUTOMATED MESSAGE>");
                }
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to log player login! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }

    @EventHandler
    protected void onPlayerDisconnectEvent(PlayerQuitEvent event) {
        try {
            if (event != null) {
                Player player = event.getPlayer();
                Location location = event.getPlayer().getLocation();
                World world = location.getWorld();
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                String message = "[> Quit <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " WORLD: " + world.getName() + " LOCATION: X=" + x + " Y=" + y + " Z=" + z + " IP: " + player.getAddress().getHostName();
                String fileName = Get.CurrentDate().replace("/", "_");
                FileManager.writeToFile("ActivityLog/" + fileName + ".txt", message);
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to log player disconnect event! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }
}