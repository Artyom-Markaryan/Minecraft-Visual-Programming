package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DefineVariableCodeBlock extends ItemStack implements PluginItem {
    public DefineVariableCodeBlock() {
        super(Material.WAXED_COPPER_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<gold>✘ Définir une variable");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Définit <gray>une <gold>variable <gray>qui stocke une <yellow>valeur "),
                getNonItalicComponent("<dark_gray>» <gray>et qui peut être <dark_green>réutilisée et modifiée")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
