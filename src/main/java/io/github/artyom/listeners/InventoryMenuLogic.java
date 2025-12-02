package io.github.artyom.listeners;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.inventorymenus.*;
import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.inventorymenus.buttons.CodeBlockFunction;
import io.github.artyom.inventorymenus.buttons.functions.AddNumbersButton;
import io.github.artyom.inventorymenus.buttons.functions.DefineButton;
import io.github.artyom.inventorymenus.buttons.loops.MultipleTimesButton;
import io.github.artyom.inventorymenus.buttons.operators.EqualsButton;
import io.github.artyom.inventorymenus.buttons.playeractions.SendMessageButton;
import io.github.artyom.inventorymenus.buttons.playerevents.CodeCompileButton;
import io.github.artyom.inventorymenus.buttons.playerevents.InteractButton;
import io.github.artyom.items.CodeBlocksItem;
import io.github.artyom.items.ValuesItem;
import io.github.artyom.items.codeblocks.*;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
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
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class InventoryMenuLogic implements Listener {
    private static final Map<Supplier<ItemStack>, Supplier<InventoryMenu>> ITEM_SUPPLIER_MENUS = Map.of(
        CodeBlocksItem::new, CodeBlocksMenu::new,
        ValuesItem::new, ValuesMenu::new
    );
    private static final NamespacedKey ITEM_SUPPLIER_MENU_KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "ItemSupplierMenu");

    private static final Map<NamespacedKey, Supplier<InventoryMenu>> CODE_BLOCK_MENUS = Map.of(
        PlayerEventCodeBlock.KEY, PlayerEventsMenu::new,
        PlayerActionCodeBlock.KEY, PlayerActionsMenu::new,
        DefineVariableCodeBlock.KEY, DefineVariableMenu::new,
        IFConditionCodeBlock.KEY, IFConditionMenu::new,
        LoopCodeBlock.KEY, LoopsMenu::new
    );
    private static final NamespacedKey CODE_BLOCK_MENU_KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "CodeBlockMenu");

    private static final Set<Supplier<ItemStack>> CODE_BLOCK_FUNCTIONS = Set.of(
        CodeCompileButton::new,
        InteractButton::new,
        SendMessageButton::new,
        DefineButton::new,
        AddNumbersButton::new,
        EqualsButton::new,
        MultipleTimesButton::new
    );

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent playerInteractEvent) {
        Action action = playerInteractEvent.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = playerInteractEvent.getPlayer();
        ItemStack eventItem = player.getInventory().getItemInMainHand();

        for (Map.Entry<Supplier<ItemStack>, Supplier<InventoryMenu>> entry : ITEM_SUPPLIER_MENUS.entrySet()) {
            if (eventItem.isSimilar(entry.getKey().get())) {
                entry.getValue().get().open(player);
                player.getPersistentDataContainer().set(ITEM_SUPPLIER_MENU_KEY, PersistentDataType.BOOLEAN, true);
                return;
            }
        }

        Block clickedBlock = playerInteractEvent.getClickedBlock();
        if (clickedBlock == null)
            return;

        if (!(clickedBlock.getState() instanceof Sign signBlockState))
            return;

        for (Map.Entry<NamespacedKey, Supplier<InventoryMenu>> entry : CODE_BLOCK_MENUS.entrySet()) {
            if (signBlockState.getPersistentDataContainer().has(entry.getKey())) {
                entry.getValue().get().open(player);
                player.getPersistentDataContainer().set(CODE_BLOCK_MENU_KEY, PersistentDataType.BOOLEAN, true);
                return;
            }
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

            if (eventItem.isSimilar(new CloseButton())) {
                clickedInventory.close();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                return;
            }

            if (player.getPersistentDataContainer().has(ITEM_SUPPLIER_MENU_KEY)) {
                player.getInventory().addItem(eventItem);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                return;
            }

            if (player.getPersistentDataContainer().has(CODE_BLOCK_MENU_KEY)) {
                CODE_BLOCK_FUNCTIONS.stream()
                    .filter(codeBlockFunction -> codeBlockFunction.get().isSimilar(eventItem))
                    .findFirst()
                    .ifPresent(
                        codeBlockFunction -> {
                            Block targetBlock = player.getTargetBlock(null, 4);
                            if (targetBlock.getState() instanceof Sign signBlockState) {
                                PersistentDataContainer codeBlockFunctionPDC = codeBlockFunction.get().getItemMeta().getPersistentDataContainer();
                                PersistentDataContainer signBlockStatePDC = signBlockState.getPersistentDataContainer();
                                codeBlockFunctionPDC.copyTo(signBlockStatePDC, true);
                                String codeBlockFunctionKeyValue = Objects.requireNonNull(signBlockStatePDC.get(CodeBlockFunction.KEY, PersistentDataType.STRING));
                                signBlockState.getSide(Side.FRONT).line(1, Component.text(codeBlockFunctionKeyValue));
                                signBlockState.update();
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                player.closeInventory();
                            }
                        }
                    );
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player) inventoryCloseEvent.getPlayer();
        Set<NamespacedKey> keysToRemove = Set.of(InventoryMenu.KEY, ITEM_SUPPLIER_MENU_KEY, CODE_BLOCK_MENU_KEY);
        keysToRemove.stream().filter(player.getPersistentDataContainer()::has).forEach(player.getPersistentDataContainer()::remove);
    }
}
