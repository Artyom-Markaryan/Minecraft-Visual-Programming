package io.github.artyom.items.values;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TextItem extends ItemStack {
    public TextItem() {
        super(Material.PAPER);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<green>✎ Texte");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Un type de <yellow>valeur <gray>qui supporte un <green>court texte"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Peut être utilisé dans <dark_green>les fonctions d'édition de texte")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
