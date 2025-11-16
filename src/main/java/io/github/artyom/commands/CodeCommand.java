package io.github.artyom.commands;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.items.CodeBlocksItem;
import io.github.artyom.items.ValuesItem;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String @NotNull [] args
    ) {
        if (!(sender instanceof Player player)) {
            Component message = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                "<red>Cette commande ne peut être exécutée que par un joueur."
            );
            sender.sendMessage(message);
            return true;
        }
        player.getInventory().clear();
        player.getInventory().setItem(7, CodeBlocksItem.getNewInstance());
        player.getInventory().setItem(8, ValuesItem.getNewInstance());
        player.playSound(player.getLocation(), "entity.item.pickup", 1, 1);
        return true;
    }
}
