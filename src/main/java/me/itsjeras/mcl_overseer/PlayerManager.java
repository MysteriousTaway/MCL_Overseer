package me.itsjeras.mcl_overseer;

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

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class PlayerManager implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
                Player player = ((Player) event).getPlayer();
                Player attacker = (Player) event.getDamager();
                double distance = player.getLocation().distance(attacker.getLocation());
                if(distance < 3.6) {
                    assert player != null;
                    Location location = player.getLocation();
                    // Location of attacked player has to be more than 8000 on x or z axis to be considered valid.
                    if(!Get.isInPvE(location)) {
                        attacker.sendMessage(ChatColor.GOLD + "<[INFO]> " + ChatColor.WHITE + "You dealt " + ChatColor.RED + event.getDamage() + " damage" + ChatColor.WHITE + " to " + player.getDisplayName());
                        String message = "[PvP] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >" + " ATTACKER: " + attacker.getDisplayName() + " VICTIM: " + player.getDisplayName() + " DAMAGE: " + event.getDamage() + " ATT_HEALTH: " + attacker.getHealth() + " VIC_HEALTH " + player.getHealth();
                        String fileName = Get.CurrentDate().replace("/", "_");
                        FileManager.writeToFile("CombatLog/" + fileName + ".txt", message);
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                    attacker.sendMessage(ChatColor.RED +  "<[!!!]> The use of REACH hack is prohibited on this server.");
                }
            }
        } catch (Exception exception) {
            System.out.println(ChatColor.DARK_RED + "<[!!!]> Overseer could not log player damage!");
            String message = "[> onPlayerDamage Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + Objects.requireNonNull(((Player) event).getPlayer()).getDisplayName();
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        try {
            Player deadPlayer = event.getEntity();
            Location location = event.getEntity().getLocation();
            String deathMessage = event.getDeathMessage();

            String message = "[> DEATH <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + deadPlayer.getDisplayName() + " DEATH MESSAGE: " + deathMessage + " LOCATION: X=" + location.getBlockX() + " Y=" + location.getBlockY() + " Z=" + location.getBlockZ();
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("DeathLog/" + fileName + ".txt", message);
        } catch (Exception exception) {
            System.out.println(ChatColor.DARK_RED + "<[!!!]> Overseer could not log player death!");
            String message = "[> onPlayerDamage Exception <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + Objects.requireNonNull(((Player) event).getPlayer()).getDisplayName();
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n");
        }
    }

    @EventHandler
    public void endPortalListener(PlayerTeleportEvent event) {
        if(!ConfigManager.Allow_End) {
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
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
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
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(event != null) {
            //Create log file:
            HonorManager.CheckForDataFile(event.getPlayer());
            //Log login:
            Player player =  event.getPlayer();
            Location location = event.getPlayer().getLocation();
            World world = location.getWorld();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            String message = "[> Join <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " WORLD: " + world.getName() + " LOCATION: X=" + x + " Y=" + y + " Z=" + z;
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("ActivityLog/" + fileName + ".txt", message);
        }
    }

    @EventHandler
    public void onPlayerDisconnectEvent(PlayerQuitEvent event) {
        if(event != null) {
            Player player =  event.getPlayer();
            Location location = event.getPlayer().getLocation();
            World world = location.getWorld();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            String message = "[> Quit <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > PLAYER: " + player.getDisplayName() + " WORLD: " + world.getName() + " LOCATION: X=" + x + " Y=" + y + " Z=" + z;
            String fileName = Get.CurrentDate().replace("/", "_");
            FileManager.writeToFile("ActivityLog/" + fileName + ".txt", message);
        }
    }
}