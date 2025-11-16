package io.github.artyom.inventories;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.items.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CodeBlocksMenu implements InventoryMenu {
    public static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "CodeBlocksMenu");

    @Override
    public void open(Player player) {
        int size = 27;
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
        int thirdRowCenterIndex = 22;
        codeBlocksMenu.setItem(thirdRowCenterIndex, new CloseMenuItem());

        player.openInventory(codeBlocksMenu);
        player.playSound(player.getLocation(), "block.note_block.harp", 1, 1);

        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        persistentDataContainer.set(KEY, PersistentDataType.BOOLEAN, true);
    }
}
