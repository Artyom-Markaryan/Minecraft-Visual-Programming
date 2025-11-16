package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ValuesItem extends PluginItem {
    public ValuesItem() {
        super(Material.CLOCK);

        ItemMeta itemMeta = this.getItemMeta();
        Component customName = super.getNonItalicComponent("<yellow>⌚ Valeurs");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                super.getNonItalicComponent("<dark_gray>» <gold><underlined><key:key.use></underlined> <gray>pour ouvrir le menu"),
                super.getNonItalicComponent("<dark_gray>» <gray>des valeurs")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
