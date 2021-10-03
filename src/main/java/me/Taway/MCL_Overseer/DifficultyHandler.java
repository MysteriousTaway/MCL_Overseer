package me.Taway.MCL_Overseer;

import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class DifficultyHandler implements Listener {
    private final int HealthMultiplier = ConfigManager.Difficulty_HealthMultiplier;
    private final boolean DropArmorOnDeath = ConfigManager.Difficulty_DropArmorOnDeath;
    private final int EquipmentChance = ConfigManager.Difficulty_EquipmentChance;

    private final int OverworldX = ConfigManager.OverworldPVE_X;
    private final int OverworldZ = ConfigManager.OverworldPVE_Z;

    private final int NetherX = ConfigManager.NetherPVE_X;
    private final int NetherZ = ConfigManager.NetherPVE_Z;
    @EventHandler
    protected void onEntitySpawn(EntitySpawnEvent event) {
//        TODO: try catch block
        if(event.getEntity() instanceof Monster) {
            int x = event.getLocation().getBlockX();
            int z = event.getLocation().getBlockZ();
            Monster entity = (Monster) event.getEntity();
            double ThousandsDistanceX = 0;
            double ThousandsDistanceZ = 0;
            switch (event.getLocation().getWorld().getName()) {
                case("world") -> {
//                Get distance (in thousands from PvE)
                    ThousandsDistanceX = x - OverworldX;
                    ThousandsDistanceX = ThousandsDistanceX / 1000;

                    ThousandsDistanceZ = z - OverworldZ;
                    ThousandsDistanceZ = ThousandsDistanceZ / 1000;
                }
                case("world_nether") -> {
//                Get distance (in thousands from PvE)
                    ThousandsDistanceX = x - NetherX;
                    ThousandsDistanceX = ThousandsDistanceX / 1000;

                    ThousandsDistanceZ = z - NetherZ;
                    ThousandsDistanceZ = ThousandsDistanceZ / 1000;
                }
            }
            if(ThousandsDistanceX > 0 && ThousandsDistanceZ > 0) {
                double HPMultiplier = HealthMultiplier * ThousandsDistanceX;
                double AbsorptionHealth = entity.getHealth() * HPMultiplier;
                entity.setAbsorptionAmount(AbsorptionHealth);
                //                    Armor shenanigans
                boolean[] ArmorChances = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    int j = Get.RandomNumInRange(0, 100);
                    if(j < EquipmentChance + 1) {
                        ArmorChances[i] = true;
                    } else {
                        ArmorChances[i] = false;
                    }
                }
//                    Head slot:
                if(ArmorChances[0]) {
                    entity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    if(!DropArmorOnDeath) {
                        entity.getEquipment().setBootsDropChance(0);
                    }
                }
//                    Body
                if(ArmorChances[1]) {
                    entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    if(!DropArmorOnDeath) {
                        entity.getEquipment().setChestplateDropChance(0);
                    }
                }
//                    Leggings
                if(ArmorChances[2]) {
                    entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    if(!DropArmorOnDeath) {
                        entity.getEquipment().setLeggingsDropChance(0);
                    }
                }
//                    Boots
                if(ArmorChances[3]) {
                    entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    if(!DropArmorOnDeath) {
                        entity.getEquipment().setBootsDropChance(0);
                    }
                }
            }
        }
    }
}
