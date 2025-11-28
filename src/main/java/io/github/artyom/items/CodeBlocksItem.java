package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CodeBlocksItem extends ItemStack {
    public CodeBlocksItem() {
        super(Material.ENCHANTED_BOOK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<aqua>⧈ Blocs de code");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <green><underlined><key:key.use></underlined> <gray>pour ouvrir le menu"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>des <aqua>blocs de code")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
