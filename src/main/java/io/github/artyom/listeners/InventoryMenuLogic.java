package io.github.artyom.listeners;

import io.github.artyom.inventorymenus.*;
import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.items.CodeBlocksItem;
import io.github.artyom.items.ValuesItem;
import io.github.artyom.items.codeblocks.*;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
            return;
        }
        if (eventItem.equals(valuesItem)) {
            ValuesMenu valuesMenu = new ValuesMenu();
            valuesMenu.open(player);
            return;
        }

        Block clickedBlock = playerInteractEvent.getClickedBlock();
        if (clickedBlock == null)
            return;

        if (!(clickedBlock.getState() instanceof Sign signBlockState))
            return;

        if (signBlockState.getPersistentDataContainer().has(PlayerEventCodeBlock.KEY)) {
            PlayerEventsMenu playerEventsMenu = new PlayerEventsMenu();
            playerEventsMenu.open(player);
            return;
        }

        if (signBlockState.getPersistentDataContainer().has(PlayerActionCodeBlock.KEY)) {
            PlayerActionsMenu playerActionsMenu = new PlayerActionsMenu();
            playerActionsMenu.open(player);
            return;
        }

        if (signBlockState.getPersistentDataContainer().has(DefineVariableCodeBlock.KEY)) {
            DefineVariableMenu defineVariableMenu = new DefineVariableMenu();
            defineVariableMenu.open(player);
            return;
        }

        if (signBlockState.getPersistentDataContainer().has(IFConditionCodeBlock.KEY)) {
            IFConditionMenu ifConditionMenu = new IFConditionMenu();
            ifConditionMenu.open(player);
            return;
        }

        if (signBlockState.getPersistentDataContainer().has(LoopCodeBlock.KEY)) {
            LoopsMenu loopsMenu = new LoopsMenu();
            loopsMenu.open(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        if (player.getPersistentDataContainer().has(InventoryMenu.KEY)) {
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
        if (player.getPersistentDataContainer().has(InventoryMenu.KEY))
            player.getPersistentDataContainer().remove(InventoryMenu.KEY);
    }
}
