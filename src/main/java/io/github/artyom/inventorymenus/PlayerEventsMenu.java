package io.github.artyom.inventorymenus;

import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.inventorymenus.buttons.playerevents.CodeCompileButton;
import io.github.artyom.inventorymenus.buttons.playerevents.InteractButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class PlayerEventsMenu implements InventoryMenu {
    @Override
    public void open(Player player) {
        int size = 18;
        Component title = Component.text("Événements du joueur");
        Inventory playerEventsMenu = Bukkit.createInventory(player, size, title);

        playerEventsMenu.setItem(0, new CodeCompileButton());
        playerEventsMenu.setItem(1, new InteractButton());
        int secondRowCenterIndex = 13;
        playerEventsMenu.setItem(secondRowCenterIndex, new CloseButton());

        player.openInventory(playerEventsMenu);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);

        player.getPersistentDataContainer().set(InventoryMenu.KEY, PersistentDataType.BOOLEAN, true);
    }
}
