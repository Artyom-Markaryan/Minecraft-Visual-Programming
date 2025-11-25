package io.github.artyom.items.values;

import io.github.artyom.items.PluginItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NumberItem extends ItemStack implements PluginItem {
    public NumberItem() {
        super(Material.SLIME_BALL);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<red>⅔ Nombre");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <gray>Un type de <yellow>valeur <gray>qui supporte un <red>nombre"),
                getNonItalicComponent("<dark_gray>» <gray>Peut être utilisé dans <dark_green>les fonctions mathématiques")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
