package io.github.artyom.commands;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.items.CodeBlocksItem;
import io.github.artyom.items.ValuesItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CodeCommand implements CommandExecutor, TabCompleter {
    List<String> options = List.of("location", "edit", "compile");

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String @NotNull [] args
    ) {
        if (!(sender instanceof Player player)) {
            Component errorMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                "<red>Cette commande ne peut être exécutée que par un joueur!"
            );
            sender.sendMessage(errorMessage);
            return true;
        }

        if (args.length < 1) {
            Component errorMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                "<red>Syntaxe attendue: <#0BDA51>/code location <green>x₁ y₁ z₁ <dark_green>x₂ y₂ z₂<red>, <#0BDA51>/code edit<red>, ou <#0BDA51>/code compile<red>."
            );
            player.sendMessage(errorMessage);
            return true;
        }

        if (args[0].equalsIgnoreCase(options.getFirst())) {
            if (args.length != 7) {
                Component errorMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                    "<red>Syntaxe attendue: <#0BDA51>/code location <green>x₁ y₁ z₁ <dark_green>x₂ y₂ z₂"
                );
                player.sendMessage(errorMessage);
                return true;
            }
            this.setCodeLocation();
        } else if (args[0].equalsIgnoreCase(options.get(1))) {
            this.setCodeInventory(player);
        } else if (args[0].equalsIgnoreCase(options.get(2))) {
            this.compileCode();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String @NotNull [] args
    ) {
        return options;
    }

    private void setCodeLocation() {
        // TODO: À implémenter...
    }

    private void setCodeInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(7, new CodeBlocksItem());
        player.getInventory().setItem(8, new ValuesItem());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

    private void compileCode() {
        // TODO: À implémenter...
    }
}
