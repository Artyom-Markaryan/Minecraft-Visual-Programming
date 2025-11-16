package io.github.artyom;

import io.github.artyom.commands.CodeCommand;
import io.github.artyom.listeners.PlayerEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftVisualProgramming extends JavaPlugin {
    private static final String PLUGIN_NAME = "Minecraft-Visual-Programming";
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {
        this.getLogger().info(PLUGIN_NAME + " is enabled!");
        this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        PluginCommand codeCommand = this.getCommand("code");
        if (codeCommand != null)
            codeCommand.setExecutor(new CodeCommand());
    }

    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        this.getLogger().info(PLUGIN_NAME + " is disabled.");
    }

    public static MinecraftVisualProgramming getInstance() {
        return getPlugin(MinecraftVisualProgramming.class);
    }
}
