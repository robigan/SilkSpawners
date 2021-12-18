package com.robigan.silkspawners.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import de.tr7zw.nbtapi.NBTItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnerBreakListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBreakEvent(@NotNull BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemInMain = player.getInventory().getItemInMainHand();
        final Block targetBlock = event.getBlock();
//        Bukkit.getLogger().warning("Parsing Break Listener, player: " + player.getName());

        if (itemInMain.containsEnchantment(Enchantment.SILK_TOUCH) && targetBlock.getType() == Material.SPAWNER) {
            final Location targetLoc = targetBlock.getLocation().add(0.5, 0.5, 0.5);

            final String entityTypeName = (((CreatureSpawner) targetBlock.getState()).getSpawnedType()).toString();

            final ItemStack targetItemStack = new ItemStack(Material.SPAWNER, 1);

            List<Component> loreList = targetItemStack.lore();
            if (loreList == null) {
                loreList = new ArrayList<>();
            }
            loreList.add(Component.text("Spawner Type: " + entityTypeName));

            targetItemStack.lore(loreList);

            final NBTItem targetNBTItem = new NBTItem(targetItemStack);
            targetNBTItem.setString("EntityType", entityTypeName);

            event.setExpToDrop(0);
            player.getWorld().dropItem(targetLoc, targetNBTItem.getItem());
        }
    }
}
