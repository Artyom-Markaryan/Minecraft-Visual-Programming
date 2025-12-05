package io.github.artyom.commands;

import io.github.artyom.inventorymenus.buttons.CodeBlockFunction;
import io.github.artyom.listeners.ConfigurableListener;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class CodeCompiler {
    private final Set<String> events = Set.of(
        "/code compile",
        "Intéraction"
    );

    public List<ConfigurableListener> readCodeLocation(CodeLocation codeLocation) {
        World world = codeLocation.firstCorner().getWorld();

        int minX = Math.min(codeLocation.firstCorner().getBlockX(), codeLocation.secondCorner().getBlockX());
        int maxX = Math.max(codeLocation.firstCorner().getBlockX(), codeLocation.secondCorner().getBlockX());
        int minY = Math.min(codeLocation.firstCorner().getBlockY(), codeLocation.secondCorner().getBlockY());
        int maxY = Math.max(codeLocation.firstCorner().getBlockY(), codeLocation.secondCorner().getBlockY());
        int minZ = Math.min(codeLocation.firstCorner().getBlockZ(), codeLocation.secondCorner().getBlockZ());
        int maxZ = Math.max(codeLocation.firstCorner().getBlockZ(), codeLocation.secondCorner().getBlockZ());

        List<ConfigurableListener> configurableListeners = new ArrayList<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block currentBlock = world.getBlockAt(x, y, z);
                    if (!(currentBlock.getState() instanceof Sign signBlockState))
                        continue;
                    if (!signBlockState.getPersistentDataContainer().has(CodeBlockFunction.KEY))
                        continue;
                    String codeBlockFunctionKeyValue = signBlockState.getPersistentDataContainer().get(CodeBlockFunction.KEY, PersistentDataType.STRING);
                    if (codeBlockFunctionKeyValue == null || !events.contains(codeBlockFunctionKeyValue))
                        continue;
                    ConfigurableListener configurableListener = new ConfigurableListener();
                    configurableListener.addSendMessageCallback("<dark_aqua>Événement déclenché!", codeBlockFunctionKeyValue);
                    configurableListeners.add(configurableListener);
                }
            }
        }

        return configurableListeners;
    }
}
