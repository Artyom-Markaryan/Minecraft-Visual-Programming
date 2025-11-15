package io.github.artyom;

import io.github.artyom.commands.CodeCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftVisualProgramming extends JavaPlugin implements Listener {
    private static final String PLUGIN_NAME = "Minecraft-Visual-Programming";
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {
        this.getLogger().info(PLUGIN_NAME + " is enabled!");
        this.getServer().getPluginManager().registerEvents(this, this);
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        Component joinMessage = MINI_MESSAGE.deserialize("<gold><bold>»</bold> <yellow>" + player.getName() + "</yellow> a rejoint le serveur!");
        playerJoinEvent.joinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        Component quitMessage = MINI_MESSAGE.deserialize("<gold><bold>»</bold> <yellow>" + player.getName() + "</yellow> a quitté le serveur.");
        playerQuitEvent.quitMessage(quitMessage);
    }
}
