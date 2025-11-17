package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ELSECodeBlock extends ItemStack implements PluginItem {
    public ELSECodeBlock() {
        super(Material.DEEPSLATE_BRICKS);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<red>≠ SINON");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>les <aqua>blocs de code <gray>à l'intérieur"),
                getNonItalicComponent("<dark_gray>» <gray>des <gold>blocs accolades <gray>si la <yellow>condition"),
                getNonItalicComponent("<dark_gray>» <gray>spécifiée est <red>fausse")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
