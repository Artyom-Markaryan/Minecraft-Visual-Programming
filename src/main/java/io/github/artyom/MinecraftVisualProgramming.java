package io.github.artyom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftVisualProgramming extends JavaPlugin implements Listener {
    private static final String PLUGIN_NAME = "Minecraft-Visual-Programming";
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {
        getLogger().info(PLUGIN_NAME + " is enabled!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        getLogger().info(PLUGIN_NAME + " is disabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        Component joinMessage = MINI_MESSAGE.deserialize("<gradient:yellow:gold>Salut " + player.getName() + "!!");
        playerJoinEvent.joinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        Component quitMessage = MINI_MESSAGE.deserialize("<gradient:aqua:dark_aqua>Au revoir " + player.getName() + " o/");
        playerQuitEvent.quitMessage(quitMessage);
    }
}
