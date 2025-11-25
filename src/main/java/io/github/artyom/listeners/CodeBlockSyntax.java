package io.github.artyom.listeners;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.items.*;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class CodeBlockSyntax implements Listener {
    private static final Set<ItemStack> CODE_BLOCKS = Set.of(
        new PlayerEventCodeBlock(),
        new PlayerActionCodeBlock(),
        new DefineVariableCodeBlock(),
        new IFConditionCodeBlock(),
        new ELSECodeBlock(),
        new LoopCodeBlock()
    );

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent blockPlaceEvent) {
        ItemStack placedBlock = blockPlaceEvent.getItemInHand();
        Block block = blockPlaceEvent.getBlock();
        Player player = blockPlaceEvent.getPlayer();
        for (ItemStack codeBlock : CODE_BLOCKS) {
            if (placedBlock.isSimilar(codeBlock)) {
                try {
                    CodeBlock placedCodeBlock = (CodeBlock) codeBlock;
                    placedCodeBlock.onBlockPlace(block, player);
                } catch (NotEnoughSpaceException e) {
                    blockPlaceEvent.setCancelled(true);
                    Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize("<red>" + e.getMessage());
                    player.sendActionBar(exceptionMessage);
                }
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockPlaceEvent blockPlaceEvent) {

    }
}
