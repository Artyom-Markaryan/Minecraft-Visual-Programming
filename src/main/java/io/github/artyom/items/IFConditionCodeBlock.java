package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IFConditionCodeBlock extends ItemStack implements PluginItem {
    public IFConditionCodeBlock() {
        super(Material.END_STONE_BRICKS);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<yellow>? SI Condition");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>les <aqua>blocs de code <gray>à l'intérieur"),
                getNonItalicComponent("<dark_gray>» <gray>des <gold>blocs accolades <gray>si la <yellow>condition"),
                getNonItalicComponent("<dark_gray>» <gray>spécifiée est <green>vraie")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
