package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LoopCodeBlock extends ItemStack implements PluginItem {
    public LoopCodeBlock() {
        super(Material.PRISMARINE);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<aqua>∑ Boucle");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Répète l'exécution <gray>des <aqua>blocs de code"),
                getNonItalicComponent("<dark_gray>» <gray>à l'intérieur des <gold>blocs accolades"),
                getNonItalicComponent("<dark_gray>» <gray>selon la <blue>façon <gray>spécifiée")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
