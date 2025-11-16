package io.github.artyom.items;

import io.github.artyom.MinecraftVisualProgramming;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

abstract class PluginItem extends ItemStack {
    PluginItem(Material material) {
        super(material);
    }

    Component getNonItalicComponent(String inputString) {
        return MinecraftVisualProgramming.MINI_MESSAGE
            .deserialize(inputString)
            .decoration(TextDecoration.ITALIC, false);
    }
}
