package io.github.artyom.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CloseMenuItem extends ItemStack implements PluginItem {
    public CloseMenuItem() {
        super(Material.BARRIER);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<red>Fermer");
        itemMeta.customName(customName);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
