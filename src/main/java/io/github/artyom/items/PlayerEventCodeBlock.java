package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerEventCodeBlock extends ItemStack implements PluginItem {
    public PlayerEventCodeBlock() {
        super(Material.CYAN_CONCRETE);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<dark_aqua>☻ Événement du joueur");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>une ligne des <gold>blocs de code <gray>lorsque"),
                getNonItalicComponent("<dark_gray>» <gray>le <yellow>joueur <light_purple>déclenche <gray>l'<dark_purple>événement <gray>spécifié")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
