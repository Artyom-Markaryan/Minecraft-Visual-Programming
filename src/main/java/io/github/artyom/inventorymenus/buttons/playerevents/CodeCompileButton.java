package io.github.artyom.inventorymenus.buttons.playerevents;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CodeCompileButton extends ItemStack {
    public CodeCompileButton() {
        super(Material.COMMAND_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<aqua>/code compile");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Déclenche l'exécution <gray>de la ligne de code quand"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>le <dark_aqua>joueur <gray>exécute la commande <#0BDA51>/code compile")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
