package io.github.artyom.inventorymenus;

import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.inventorymenus.buttons.functions.AddNumbersButton;
import io.github.artyom.inventorymenus.buttons.functions.DefineButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class DefineVariableMenu implements InventoryMenu {
    @Override
    public void open(Player player) {
        int size = 18;
        Component title = Component.text("Définir une variable");
        Inventory defineVariableMenu = Bukkit.createInventory(player, size, title);

        defineVariableMenu.setItem(0, new DefineButton());
        defineVariableMenu.setItem(1, new AddNumbersButton());
        int secondRowCenterIndex = 13;
        defineVariableMenu.setItem(secondRowCenterIndex, new CloseButton());

        player.openInventory(defineVariableMenu);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);

        player.getPersistentDataContainer().set(InventoryMenu.KEY, PersistentDataType.BOOLEAN, true);
    }
}
