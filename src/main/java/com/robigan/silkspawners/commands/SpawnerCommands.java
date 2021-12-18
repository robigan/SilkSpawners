package com.robigan.silkspawners.commands;

import de.tr7zw.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpawnerCommands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof final Player playerSender)) {
            sender.sendMessage("Due to the nature of this plugin, a player with perm node silkspawners.command can only issue this command");
            return false;
        }

        if (!(playerSender.hasPermission("silkspawners.command"))) {
            sender.sendMessage("Not allowed to execute this command");
            return false;
        }

        if (!(args.length == 2)) {
            return false;
        }

        EntityType targetMobType;
        try {
            targetMobType = EntityType.valueOf(args[0]);
        } catch (IllegalArgumentException error) {
            playerSender.sendMessage("The EntityType used doesn't seem to exist (IllegalArgumentException)");
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            playerSender.sendMessage("Please give a valid number");
            return false;
        }

        if (amount < 1) {
            playerSender.sendMessage("Please give a valid number");
            return false;
        }

        final ItemStack targetItemStack = new ItemStack(Material.SPAWNER, amount);

        List<Component> loreList = targetItemStack.lore();
        if (loreList == null) {
            loreList = new ArrayList<>();
        }
        loreList.add(Component.text("Spawner Type: " + targetMobType));

        targetItemStack.lore(loreList);

        final NBTItem targetNBTItem = new NBTItem(targetItemStack);
        targetNBTItem.setString("EntityType", targetMobType.toString());

        playerSender.getInventory().addItem(targetNBTItem.getItem());

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();

        if (!(sender instanceof final Player playerSender)) {
            return list;
        }

        if (!(playerSender.hasPermission("silkspawners.command"))) {
            return list;
        }

        if (args.length == 1) {
            for (EntityType type : EntityType.values()) {
                if (type.isAlive() && type.isSpawnable()) {
                    list.add(type.toString());
                }
            }
        } else if (args.length == 2) {
            list.add("1");
        }

        return list;
    }
}
