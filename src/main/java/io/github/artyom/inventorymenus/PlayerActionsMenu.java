package io.github.artyom.inventorymenus;

import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.inventorymenus.buttons.playeractions.SendMessageButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class PlayerActionsMenu implements InventoryMenu {
    @Override
    public void open(Player player) {
        int size = 18;
        Component title = Component.text("Actions du joueur");
        Inventory playerActionsMenu = Bukkit.createInventory(player, size, title);

        playerActionsMenu.setItem(0, new SendMessageButton());
        int secondRowCenterIndex = 13;
        playerActionsMenu.setItem(secondRowCenterIndex, new CloseButton());

        player.openInventory(playerActionsMenu);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);

        player.getPersistentDataContainer().set(InventoryMenu.KEY, PersistentDataType.BOOLEAN, true);
    }
}
