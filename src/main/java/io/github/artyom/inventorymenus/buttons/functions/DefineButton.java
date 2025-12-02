package io.github.artyom.inventorymenus.buttons.functions;

import io.github.artyom.inventorymenus.buttons.CodeBlockFunction;
import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DefineButton extends ItemStack {
    public DefineButton() {
        super(Material.IRON_INGOT);

        ItemMeta itemMeta = super.getItemMeta();
        Component title = ServerItem.getNonItalicComponent("<gold>Définir une variable (=)");
        itemMeta.customName(title);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Définit <gray>une <gold>variable <gray>avec la <yellow>valeur <gray>spécifiée"),
                ServerItem.getNonItalicComponent(""),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Dans le <gold>coffre de paramètres<gray>:"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gold>✘ Variable <gray>- La variable à définir"),
                ServerItem.getNonItalicComponent("<dark_gray>» <yellow>⌚ N'importe quelle valeur <gray>- La valeur de cette variable")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        itemMeta.getPersistentDataContainer().set(CodeBlockFunction.KEY, PersistentDataType.STRING, "=");
        this.setItemMeta(itemMeta);
    }
}
