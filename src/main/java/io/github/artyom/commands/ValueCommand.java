package io.github.artyom.commands;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.IncorrectMainHandItemException;
import io.github.artyom.items.ServerItem;
import io.github.artyom.items.values.NumberItem;
import io.github.artyom.items.values.TextItem;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ValueCommand implements CommandExecutor {
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
                "<red>Merci de spécifier la valeur à définir."
            );
            player.sendMessage(errorMessage);
            return true;
        }

        try {
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            player.getInventory().setItemInMainHand(this.setValue(mainHand, args));

            Component successMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize("<green>La valeur a été correctement définie!");
            player.sendMessage(successMessage);
        } catch (IncorrectMainHandItemException e) {
            Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(e.getMessage());
            player.sendMessage(exceptionMessage);
        } catch (NumberFormatException e) {
            Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize("<red>Ce type de valeur ne supporte que des nombres!");
            player.sendMessage(exceptionMessage);
        }
        return true;
    }

    private ItemStack setValue(@NotNull ItemStack mainHandItem, @NotNull String @NotNull [] playerInput) throws IncorrectMainHandItemException {
        if (!(mainHandItem.isSimilar(new TextItem()) || mainHandItem.isSimilar(new NumberItem())))
            throw new IncorrectMainHandItemException(
                "<red>Tu dois tenir un objet <green><underlined>✎ Texte</underlined> <red>ou un objet <underlined>⅔ Nombre</underlined> dans la main principale pour utiliser cette commande!"
            );

        String variableValue = String.join(" ", playerInput);
        if (mainHandItem.isSimilar(new NumberItem()))
            // noinspection ResultOfMethodCallIgnored
            Double.parseDouble(variableValue);

        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        mainHandItemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>" + variableValue)
            )
        );
        mainHandItem.setItemMeta(mainHandItemMeta);

        return mainHandItem;
    }
}
