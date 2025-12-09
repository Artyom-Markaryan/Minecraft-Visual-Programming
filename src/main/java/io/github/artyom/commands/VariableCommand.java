package io.github.artyom.commands;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.IncorrectMainHandItemException;
import io.github.artyom.items.ServerItem;
import io.github.artyom.items.values.VariableItem;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VariableCommand implements CommandExecutor {
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
                "<red>Merci de spécifier le nom de la variable."
            );
            player.sendMessage(errorMessage);
            return true;
        }

        try {
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            player.getInventory().setItemInMainHand(this.setVariableName(mainHand, args));

            Component successMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize("<green>La variable a été correctement nommée!");
            player.sendMessage(successMessage);
        } catch (IncorrectMainHandItemException e) {
            Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(e.getMessage());
            player.sendMessage(exceptionMessage);
        }
        return true;
    }

    private ItemStack setVariableName(@NotNull ItemStack mainHandItem, @NotNull String @NotNull [] playerInput) throws IncorrectMainHandItemException {
        if (!(mainHandItem.isSimilar(new VariableItem())))
            throw new IncorrectMainHandItemException(
                "<red>Tu dois tenir un objet <gold><underlined>✘ Variable</underlined> <red>dans la main principale pour utiliser cette commande!"
            );

        String variableName = String.join(" ", playerInput);

        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        mainHandItemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>" + variableName)
            )
        );
        mainHandItem.setItemMeta(mainHandItemMeta);

        return mainHandItem;
    }
}
