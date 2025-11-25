package io.github.artyom.items;

import io.github.artyom.exceptions.NotEnoughSpaceException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.List;

public interface CodeBlock {
    void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException;
    
    default BlockFace rightOf(BlockFace cardinalDirection) {
        return switch (cardinalDirection) {
            case NORTH -> BlockFace.EAST;
            case SOUTH -> BlockFace.WEST;
            case EAST -> BlockFace.SOUTH;
            case WEST -> BlockFace.NORTH;
            default -> BlockFace.SELF;
        };
    }

    default BlockFace leftOf(BlockFace cardinalDirection) {
        return switch (cardinalDirection) {
            case NORTH -> BlockFace.WEST;
            case SOUTH -> BlockFace.EAST;
            case EAST -> BlockFace.NORTH;
            case WEST -> BlockFace.SOUTH;
            default -> BlockFace.SELF;
        };
    }

    default void checkSurroundingBlocks(List<Block> surroundingBlocks) throws NotEnoughSpaceException {
        for (Block block : surroundingBlocks) {
            if (block.getType() != Material.AIR)
                throw new NotEnoughSpaceException();
        }
    }
}
