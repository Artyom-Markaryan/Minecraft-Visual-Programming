package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ValuesItem {
    public static ItemStack getNewInstance() {
        ItemStack valuesItem = new ItemStack(Material.KNOWLEDGE_BOOK);

        ItemMeta itemMeta = valuesItem.getItemMeta();
        Component customName = PluginItem.getNonItalicComponent("<green>⌚ Valeurs");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                PluginItem.getNonItalicComponent("<dark_gray>» <gold><underlined><key:key.use></underlined> <gray>pour ouvrir le menu"),
                PluginItem.getNonItalicComponent("<dark_gray>» <gray>des valeurs")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        valuesItem.setItemMeta(itemMeta);

        return valuesItem;
    }
}
