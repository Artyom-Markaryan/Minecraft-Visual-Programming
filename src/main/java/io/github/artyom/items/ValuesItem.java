package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ValuesItem {
    public static ItemStack getNewInstance() {
        ItemStack valuesItem = new ItemStack(Material.KNOWLEDGE_BOOK);

        ItemMeta itemMeta = valuesItem.getItemMeta();
        Component customName = PluginItem.getCustomName("<green>Valeurs");
        itemMeta.customName(customName);
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        valuesItem.setItemMeta(itemMeta);

        return valuesItem;
    }
}
