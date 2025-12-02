package io.github.artyom.inventorymenus.buttons.playerevents;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InteractButton extends ItemStack {
    public InteractButton() {
        super(Material.DIAMOND_PICKAXE);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<green>Intéraction");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Déclenche l'exécution <gray>de la ligne des <aqua>blocs de code"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>quand le <dark_aqua>joueur <green>clique gauche <gray>ou <green>clique droit")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(itemMeta);
    }
}
