package io.github.artyom.items;

import io.github.artyom.MinecraftVisualProgramming;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

interface PluginItem {
    default Component getNonItalicComponent(String inputString) {
        return MinecraftVisualProgramming.MINI_MESSAGE
            .deserialize(inputString)
            .decoration(TextDecoration.ITALIC, false);
    }
}
