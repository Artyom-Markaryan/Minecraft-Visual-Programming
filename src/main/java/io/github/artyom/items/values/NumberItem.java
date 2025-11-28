package io.github.artyom.items.values;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NumberItem extends ItemStack {
    public NumberItem() {
        super(Material.SLIME_BALL);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<red>⅔ Nombre");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Un type de <yellow>valeur <gray>qui supporte un <red>nombre"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Peut être utilisé dans <dark_green>les fonctions mathématiques")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
