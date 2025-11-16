package io.github.artyom.listeners;

import io.github.artyom.MinecraftVisualProgramming;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        Component joinMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
            "<gold><bold>»</bold> <yellow>" + player.getName() + "</yellow> a rejoint le serveur!"
        );
        playerJoinEvent.joinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        Component quitMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
            "<gold><bold>»</bold> <yellow>" + player.getName() + "</yellow> a quitté le serveur."
        );
        playerQuitEvent.quitMessage(quitMessage);
    }
}
