package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerActionCodeBlock extends ItemStack implements PluginItem {
    public PlayerActionCodeBlock() {
        super(Material.CYAN_WOOL);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<dark_aqua>☃ Action du joueur");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Effectue <gray>une <dark_purple>action <gray>liée au <yellow>joueur")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }
}
