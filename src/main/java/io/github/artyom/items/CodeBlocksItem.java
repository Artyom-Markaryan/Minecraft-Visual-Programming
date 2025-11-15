package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CodeBlocksItem {
    public static ItemStack getNewInstance() {
        ItemStack codeBlocksItem = new ItemStack(Material.DIAMOND);

        ItemMeta itemMeta = codeBlocksItem.getItemMeta();
        Component customName = PluginItem.getCustomName("<aqua>Blocs de code");
        itemMeta.customName(customName);
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        codeBlocksItem.setItemMeta(itemMeta);

        return codeBlocksItem;
    }
}
