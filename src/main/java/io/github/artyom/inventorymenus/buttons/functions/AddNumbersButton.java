package io.github.artyom.inventorymenus.buttons.functions;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AddNumbersButton extends ItemStack {
    public AddNumbersButton() {
        super(Material.GLOWSTONE_DUST);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<green>Additionner des nombres (+)");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Additionne <gray>plusieurs <red>nombres <gray>ensemble et retourne la <green>somme"),
                ServerItem.getNonItalicComponent(""),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Dans le <gold>coffre de paramètres<gray>:"),
                ServerItem.getNonItalicComponent("<dark_gray>» <red>⅔ Nombres <gray>- Les nombres à additionner")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
