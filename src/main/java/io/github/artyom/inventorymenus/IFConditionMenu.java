package io.github.artyom.inventorymenus;

import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.inventorymenus.buttons.operators.EqualsButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class IFConditionMenu implements InventoryMenu {
    @Override
    public void open(Player player) {
        int size = 18;
        Component title = Component.text("Condition IF");
        Inventory ifConditionMenu = Bukkit.createInventory(player, size, title);

        ifConditionMenu.setItem(0, new EqualsButton());
        int secondRowCenterIndex = 13;
        ifConditionMenu.setItem(secondRowCenterIndex, new CloseButton());

        player.openInventory(ifConditionMenu);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);

        player.getPersistentDataContainer().set(InventoryMenu.KEY, PersistentDataType.BOOLEAN, true);
    }
}
