package io.github.artyom.items.values;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class VariableItem extends ItemStack {
    public VariableItem() {
        super(Material.MAGMA_CREAM);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<gold>✘ Variable");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Une <gold>variable <gray>qui stocke une <yellow>valeur"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>et qui peut être <dark_green>réutilisée et modifiée")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
