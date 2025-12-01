package io.github.artyom.inventorymenus.buttons.operators;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EqualsButton extends ItemStack {
    public EqualsButton() {
        super(Material.GOLD_INGOT);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<yellow>Est égal à (==)");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Vérifie <gray>si une valeur <yellow>est égale à <gray>une autre valeur"),
                ServerItem.getNonItalicComponent(""),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Dans le <gold>coffre de paramètres<gray>:"),
                ServerItem.getNonItalicComponent("<dark_gray>» <yellow>⌚ N'importe quelle valeur <gray>- La valeur à vérifier"),
                ServerItem.getNonItalicComponent("<dark_gray>» <yellow>⌚ N'importe quelle valeur <gray>- L'autre valeur")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
