package io.github.artyom.items;

import io.github.artyom.exceptions.NotEnoughSpaceException;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;

import java.util.List;

public interface CodeBlock {
    void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException;

    default void placeSeparatorBlock(Block codeBlock, Player player) {
        Block separatorBlock = codeBlock.getRelative(rightOf(player.getFacing()), 1);
        separatorBlock.setType(Material.STONE);
    }

    default void placeBracketBlocks(Block codeBlock, Player player, Material bracketBlock) {
        Block openingBracketBlock = codeBlock.getRelative(rightOf(player.getFacing()), 1);
        openingBracketBlock.setType(bracketBlock);
        Directional openingBracketBlockData = (Directional) openingBracketBlock.getBlockData();
        openingBracketBlockData.setFacing(rightOf(player.getFacing()));
        openingBracketBlock.setBlockData(openingBracketBlockData);

        Block closingBracketBlock = codeBlock.getRelative(rightOf(player.getFacing()), 2);
        closingBracketBlock.setType(bracketBlock);
        Directional closingBracketBlockData = (Directional) closingBracketBlock.getBlockData();
        closingBracketBlockData.setFacing(leftOf(player.getFacing()));
        closingBracketBlock.setBlockData(closingBracketBlockData);
    }

    default void placeSignBlock(Block codeBlock, Player player, String signTitle) {
        Block signBlock = codeBlock.getRelative(player.getFacing().getOppositeFace(), 1);
        signBlock.setType(Material.OAK_WALL_SIGN);
        Directional signBlockData = (Directional) signBlock.getBlockData();
        signBlockData.setFacing(player.getFacing().getOppositeFace());
        signBlock.setBlockData(signBlockData);
        Sign signBlockState = (Sign) signBlock.getState();
        signBlockState.getSide(Side.FRONT).line(0, Component.text(signTitle));
        signBlockState.getSide(Side.FRONT).setColor(DyeColor.WHITE);
        signBlockState.getSide(Side.FRONT).setGlowingText(true);
        signBlockState.setWaxed(true);
        signBlockState.update();
    }

    default void placeChestBlock(Block codeBlock, Player player) {
        Block chestBlock = codeBlock.getRelative(BlockFace.UP, 1);
        chestBlock.setType(Material.CHEST);
        Directional chestBlockData = (Directional) chestBlock.getBlockData();
        chestBlockData.setFacing(player.getFacing().getOppositeFace());
        chestBlock.setBlockData(chestBlockData);
    }
    
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
