package io.github.artyom.items;

import io.github.artyom.MinecraftVisualProgramming;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

abstract class PluginItem {
    protected static Component getCustomName(String customName) {
        return MinecraftVisualProgramming.MINI_MESSAGE
            .deserialize(customName)
            .decoration(TextDecoration.ITALIC, false);
    }
}
