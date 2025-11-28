package io.github.artyom.items.codeblocks;

import io.github.artyom.exceptions.NotEnoughSpaceException;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface CodeBlock {
    void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException;
}
