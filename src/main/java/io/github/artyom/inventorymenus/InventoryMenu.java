package io.github.artyom.inventorymenus;

import io.github.artyom.MinecraftVisualProgramming;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public interface InventoryMenu {
    NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "InventoryMenu");

    void open(Player player);
}
