package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ValuesItem extends ItemStack {
    public ValuesItem() {
        super(Material.CLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<yellow>⌚ Valeurs");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <green><underlined><key:key.use></underlined> <gray>pour ouvrir le menu"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>des <yellow>valeurs")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
