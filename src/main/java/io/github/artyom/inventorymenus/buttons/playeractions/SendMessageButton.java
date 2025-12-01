package io.github.artyom.inventorymenus.buttons.playeractions;

import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SendMessageButton extends ItemStack {
    public SendMessageButton() {
        super(Material.WRITABLE_BOOK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<dark_green>Envoyer un message");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Envoie <gray>un message dans le chat du <dark_aqua>joueur"),
                ServerItem.getNonItalicComponent(""),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>Dans le <gold>coffre de paramètres<gray>:"),
                ServerItem.getNonItalicComponent("<dark_gray>» <green>✎ Texte <gray>- Le message à envoyer")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        this.setItemMeta(itemMeta);
    }
}
