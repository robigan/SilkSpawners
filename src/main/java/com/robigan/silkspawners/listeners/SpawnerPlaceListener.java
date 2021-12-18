package com.robigan.silkspawners.listeners;

import de.tr7zw.nbtapi.NBTItem;
import com.robigan.silkspawners.SilkSpawners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpawnerPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlaceEvent(@NotNull BlockPlaceEvent event) {
        final ItemStack itemInMain = event.getPlayer().getInventory().getItemInMainHand();
        final Block targetBlock = event.getBlockPlaced();
        final NBTItem targetNBT = new NBTItem(event.getItemInHand());
//        Bukkit.getLogger().warning("Parsing Place Listener, player: " + event.getPlayer().getName());

        if (targetBlock.getType() == Material.SPAWNER && itemInMain.getType() == Material.SPAWNER) {
            final SilkSpawners pluginInstance = SilkSpawners.getInstance();
            final EntityType type = EntityType.valueOf(targetNBT.getString("EntityType"));

            Bukkit.getScheduler().runTaskLater(pluginInstance, () -> {
                if(event.isCancelled()) {
                    event.setCancelled(true);
                } else {
                    final CreatureSpawner spawner = ((CreatureSpawner) targetBlock.getState());
                    spawner.setSpawnedType(type);
                }
            }, 5);
        }
    }
}
