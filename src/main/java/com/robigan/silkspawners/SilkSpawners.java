package com.robigan.silkspawners;

import com.robigan.silkspawners.listeners.SpawnerBreakListener;
import com.robigan.silkspawners.listeners.SpawnerPlaceListener;
import com.robigan.silkspawners.commands.SpawnerCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SilkSpawners extends JavaPlugin {

    private static SilkSpawners instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getServer().getPluginManager().registerEvents(new SpawnerBreakListener(), instance);
        Bukkit.getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(), instance);

        final PluginCommand spawnerCommand = Bukkit.getServer().getPluginCommand("spawner");
        assert spawnerCommand != null;

        spawnerCommand.setExecutor(new SpawnerCommands());
        spawnerCommand.setTabCompleter(new SpawnerCommands());
    }

/*    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }*/

    public static SilkSpawners getInstance() {
        return instance;
    }
}
