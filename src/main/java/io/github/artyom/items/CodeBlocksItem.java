package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CodeBlocksItem extends PluginItem {
    public CodeBlocksItem() {
        super(Material.DIAMOND);

        ItemMeta itemMeta = this.getItemMeta();
        Component customName = super.getNonItalicComponent("<aqua>⧈ Blocs de code");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                super.getNonItalicComponent("<dark_gray>» <gold><underlined><key:key.use></underlined> <gray>pour ouvrir le menu"),
                super.getNonItalicComponent("<dark_gray>» <gray>des blocs de code")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
