package io.github.artyom.inventorymenus.buttons.loops;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MultipleTimesButton extends ItemStack {
    public MultipleTimesButton() {
        super(Material.REDSTONE);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<red>Plusieurs fois");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Répète l'exécution <gray>des <aqua>blocs de code <gray>à l'intérieur"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>des <gold>blocs accolades <gray>un nombre de fois"),
                ServerItem.getNonItalicComponent(""),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Dans le <gold>coffre de paramètres<gray>:"),
                ServerItem.getNonItalicComponent("<dark_gray>» <red>⅔ Nombre <gray>- Le nombre de fois à répéter")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
