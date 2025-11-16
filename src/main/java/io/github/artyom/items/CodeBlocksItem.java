package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CodeBlocksItem {
    public static ItemStack getNewInstance() {
        ItemStack codeBlocksItem = new ItemStack(Material.DIAMOND);

        ItemMeta itemMeta = codeBlocksItem.getItemMeta();
        Component customName = PluginItem.getNonItalicComponent("<aqua>⧈ Blocs de code");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                PluginItem.getNonItalicComponent("<dark_gray>» <gold><underlined><key:key.use></underlined> <gray>pour ouvrir le menu"),
                PluginItem.getNonItalicComponent("<dark_gray>» <gray>des blocs de code")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        codeBlocksItem.setItemMeta(itemMeta);

        return codeBlocksItem;
    }
}
