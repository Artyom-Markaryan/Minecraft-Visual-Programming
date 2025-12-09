package io.github.artyom;

import io.github.artyom.commands.CodeCommand;
import io.github.artyom.commands.ValueCommand;
import io.github.artyom.commands.VariableCommand;
import io.github.artyom.listeners.CodeBlockSyntax;
import io.github.artyom.listeners.InventoryMenuLogic;
import io.github.artyom.listeners.SimplePlayerEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MinecraftVisualProgramming extends JavaPlugin {
    private static final String PLUGIN_NAME = "Minecraft-Visual-Programming";
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {
        this.getLogger().info(PLUGIN_NAME + " is enabled!");

        List<Listener> listeners = List.of(
            new SimplePlayerEvents(),
            new InventoryMenuLogic(),
            new CodeBlockSyntax()
        );
        for (Listener listener : listeners)
            this.getServer().getPluginManager().registerEvents(listener, this);

        try {
            Map<PluginCommand, CommandExecutor> commands = Map.of(
                Objects.requireNonNull(this.getCommand("code")), new CodeCommand(),
                Objects.requireNonNull(this.getCommand("variable")), new VariableCommand(),
                Objects.requireNonNull(this.getCommand("value")), new ValueCommand()
            );
            for (Map.Entry<PluginCommand, CommandExecutor> command : commands.entrySet())
                command.getKey().setExecutor(command.getValue());
        } catch (NullPointerException e) {
            this.getLogger().severe("Failed to register commands! A command was not found in the plugin.yml file!");
        }
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
