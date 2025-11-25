package io.github.artyom.items.values;

import io.github.artyom.items.PluginItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class VariableItem extends ItemStack implements PluginItem {
    public VariableItem() {
        super(Material.MAGMA_CREAM);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<gold>✘ Variable");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <gray>Une <gold>variable <gray>qui stocke une <yellow>valeur"),
                getNonItalicComponent("<dark_gray>» <gray>et qui peut être <dark_green>réutilisée et modifiée")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
