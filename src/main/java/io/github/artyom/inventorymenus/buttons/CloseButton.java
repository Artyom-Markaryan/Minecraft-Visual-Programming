package io.github.artyom.inventorymenus.buttons;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CloseButton extends ItemStack {
    public CloseButton() {
        super(Material.BARRIER);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<red>❌ Fermer");
        itemMeta.customName(customName);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
