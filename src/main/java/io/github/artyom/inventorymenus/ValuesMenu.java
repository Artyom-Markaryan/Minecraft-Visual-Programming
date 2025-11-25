package io.github.artyom.inventorymenus;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.inventorymenus.buttons.CloseButton;
import io.github.artyom.items.values.NumberItem;
import io.github.artyom.items.values.TextItem;
import io.github.artyom.items.values.VariableItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ValuesMenu implements InventoryMenu {
    public static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "ValuesMenu");

    @Override
    public void open(Player player) {
        int size = 18;
        Component title = Component.text("Valeurs");
        Inventory valuesMenu = Bukkit.createInventory(player, size, title);

        List<ItemStack> values = List.of(
            new VariableItem(),
            new TextItem(),
            new NumberItem()
        );
        for (ItemStack value : values)
            valuesMenu.addItem(value);
        int secondRowCenterIndex = 13;
        valuesMenu.setItem(secondRowCenterIndex, new CloseButton());

        player.openInventory(valuesMenu);
        player.playSound(player.getLocation(), "block.note_block.harp", 1, 1);

        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        persistentDataContainer.set(KEY, PersistentDataType.BOOLEAN, true);
    }
}
