package io.github.artyom.listeners;

import io.github.artyom.inventorymenus.CodeBlocksMenu;
import io.github.artyom.inventorymenus.ValuesMenu;
import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.items.CodeBlocksItem;
import io.github.artyom.items.ValuesItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class InventoryMenuLogic implements Listener {
    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent playerInteractEvent) {
        Action action = playerInteractEvent.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = playerInteractEvent.getPlayer();
        ItemStack eventItem = player.getInventory().getItemInMainHand();
        ItemStack codeBlocksItem = new CodeBlocksItem();
        ItemStack valuesItem = new ValuesItem();
        if (eventItem.equals(codeBlocksItem)) {
            CodeBlocksMenu codeBlocksMenu = new CodeBlocksMenu();
            codeBlocksMenu.open(player);
        }
        if (eventItem.equals(valuesItem)) {
            ValuesMenu valuesMenu = new ValuesMenu();
            valuesMenu.open(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        if (persistentDataContainer.has(CodeBlocksMenu.KEY) || persistentDataContainer.has(ValuesMenu.KEY)) {
            inventoryClickEvent.setCancelled(true);

            Inventory clickedInventory = inventoryClickEvent.getClickedInventory();
            if (clickedInventory == null || clickedInventory.equals(player.getInventory()))
                return;

            ItemStack eventItem = inventoryClickEvent.getCurrentItem();
            if (eventItem == null)
                return;

            if (eventItem.equals(new CloseButton())) {
                clickedInventory.close();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                return;
            }

            player.getInventory().addItem(eventItem);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player) inventoryCloseEvent.getPlayer();
        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        if (persistentDataContainer.has(CodeBlocksMenu.KEY))
            persistentDataContainer.remove(CodeBlocksMenu.KEY);
        if (persistentDataContainer.has(ValuesMenu.KEY))
            persistentDataContainer.remove(ValuesMenu.KEY);
    }
}
