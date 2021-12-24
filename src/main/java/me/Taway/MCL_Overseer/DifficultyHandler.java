package me.Taway.MCL_Overseer;

import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class DifficultyHandler implements Listener {
    private final int DropArmorOnDeathChance = ConfigManager.Difficulty_DropArmorOnDeathChance;
    private final int EquipmentChance = ConfigManager.Difficulty_EquipmentChance;

    private final int OverworldX = ConfigManager.OverworldPVE_X;
    private final int OverworldZ = ConfigManager.OverworldPVE_Z;

    private final int NetherX = ConfigManager.NetherPVE_X;
    private final int NetherZ = ConfigManager.NetherPVE_Z;
    @EventHandler
    protected void onEntitySpawn(EntitySpawnEvent event) {
//        TODO: THIS!
        try {
            if (event.getEntity() instanceof Monster) {
                int x = event.getLocation().getBlockX();
                int z = event.getLocation().getBlockZ();
                Monster entity = (Monster) event.getEntity();
                double ThousandsDistanceX = 0;
                double ThousandsDistanceZ = 0;
                switch (event.getLocation().getWorld().getName()) {
                    case ("world") -> {
//                Get distance (in thousands from PvE)
                        ThousandsDistanceX = x - OverworldX;
                        ThousandsDistanceX = ThousandsDistanceX / 1000;

                        ThousandsDistanceZ = z - OverworldZ;
                        ThousandsDistanceZ = ThousandsDistanceZ / 1000;
                    }
                    case ("world_nether") -> {
//                Get distance (in thousands from PvE)
                        ThousandsDistanceX = x - NetherX;
                        ThousandsDistanceX = ThousandsDistanceX / 1000;

                        ThousandsDistanceZ = z - NetherZ;
                        ThousandsDistanceZ = ThousandsDistanceZ / 1000;
                    }
                }

                LoggerInstance.warning("X[" + ThousandsDistanceX + "]   Z[" + ThousandsDistanceZ + "]");

                if (ThousandsDistanceX > 0 || ThousandsDistanceZ > 0) {

                    double ThousandsDistance;
                    if (ThousandsDistanceX > ThousandsDistanceZ) {
                        ThousandsDistance = ThousandsDistanceX / ThousandsDistanceZ;
                    } else if (ThousandsDistanceX < ThousandsDistanceZ) {
                        ThousandsDistance = ThousandsDistanceZ / ThousandsDistanceX;
                    } else {
                        ThousandsDistance = ThousandsDistanceZ / 2;
                    }

                    if (ThousandsDistance < 0) {
                        ThousandsDistance *= -1;
                    }
                    double AbsorptionHealth = entity.getHealth() * ThousandsDistance;
                    entity.setAbsorptionAmount(AbsorptionHealth);

                    LoggerInstance.warning(entity.getName() + " >>> HP[" + entity.getHealth() + "] ABSP[" + AbsorptionHealth + "]");
                    LoggerInstance.info("[" + ThousandsDistance + "] TD_X/TD_Z = " + ThousandsDistanceX + "/" + ThousandsDistanceZ + "  X/Z = " + x + "/" + z);

                    //                    Armor shenanigans
                    boolean[] ArmorChances = new boolean[4];
                    for (int i = 0; i < 4; i++) {
                        int j = Get.RandomNumInRange(0, 100);
                        if (j < EquipmentChance + 1) {
                            ArmorChances[i] = true;
                        } else {
                            ArmorChances[i] = false;
                        }
                    }
//                    Head slot:
                    if (ArmorChances[0]) {
                        entity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        entity.getEquipment().setBootsDropChance(DropArmorOnDeathChance);
                    }
//                    Body
                    if (ArmorChances[1]) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setChestplateDropChance(DropArmorOnDeathChance);
                    }
//                    Leggings
                    if (ArmorChances[2]) {
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setLeggingsDropChance(DropArmorOnDeathChance);
                    }
//                    Boots
                    if (ArmorChances[3]) {
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        entity.getEquipment().setBootsDropChance(DropArmorOnDeathChance);
                    }
                }
            }
        } catch (Exception exception) {
            String message = "<DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " > Error occurred while attempting to increase monster HP! ";
            String method = this.getClass().getName() + "." + this.getClass().getEnclosingMethod().getName();
            message = "[" + method + "]" + message + "\n" + message + exception.getMessage();
            FileManager.LogException(message);
        }
    }
}
