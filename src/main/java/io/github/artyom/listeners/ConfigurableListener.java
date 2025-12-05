package io.github.artyom.listeners;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.events.PlayerCodeCompileEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConfigurableListener implements Listener {
    private final List<Consumer<PlayerCodeCompileEvent>> playerCodeCompileCallbacks = new ArrayList<>();
    private final List<Consumer<PlayerInteractEvent>> playerInteractCallbacks = new ArrayList<>();

    public void addSendMessageCallback(String message, String playerEvent) {
        Component deserializedMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(message);
        switch (playerEvent) {
            case "/code compile":
                this.playerCodeCompileCallbacks.add(
                    playerCodeCompileEvent -> playerCodeCompileEvent.getPlayer().sendMessage(deserializedMessage)
                );
                break;
            case "Intéraction":
                this.playerInteractCallbacks.add(
                    playerInteractEvent -> playerInteractEvent.getPlayer().sendMessage(deserializedMessage)
                );
                break;
        }
    }

    @EventHandler
    public void onPlayerCodeCompile(PlayerCodeCompileEvent playerCodeCompileEvent) {
        if (this.playerCodeCompileCallbacks.isEmpty())
            return;
        this.playerCodeCompileCallbacks.forEach(callback -> callback.accept(playerCodeCompileEvent));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        if (this.playerInteractCallbacks.isEmpty())
            return;
        this.playerInteractCallbacks.forEach(callback -> callback.accept(playerInteractEvent));
    }
}
