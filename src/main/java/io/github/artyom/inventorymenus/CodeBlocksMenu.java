package io.github.artyom.inventorymenus;

import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.items.codeblocks.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CodeBlocksMenu implements InventoryMenu {
    @Override
    public void open(Player player) {
        int size = 18;
        Component title = Component.text("Blocs de code");
        Inventory codeBlocksMenu = Bukkit.createInventory(player, size, title);

        List<ItemStack> codeBlocks = List.of(
            new PlayerEventCodeBlock(),
            new PlayerActionCodeBlock(),
            new DefineVariableCodeBlock(),
            new IFConditionCodeBlock(),
            new ELSECodeBlock(),
            new LoopCodeBlock()
        );
        for (ItemStack codeBlock : codeBlocks)
            codeBlocksMenu.addItem(codeBlock);
        int secondRowCenterIndex = 13;
        codeBlocksMenu.setItem(secondRowCenterIndex, new CloseButton());

        player.openInventory(codeBlocksMenu);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);

        player.getPersistentDataContainer().set(InventoryMenu.KEY, PersistentDataType.BOOLEAN, true);
    }
}
