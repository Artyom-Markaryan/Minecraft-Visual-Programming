package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public interface CodeBlock {
    NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "SeparatorBlock/BracketBlock");

    void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException;

    default void placeSeparatorBlock(Block codeBlock, Player player) {
        Block separatorBlock = codeBlock.getRelative(rightOf(player.getFacing()), 1);
        separatorBlock.setType(Material.OBSERVER);
        Directional separatorBlockData = (Directional) separatorBlock.getBlockData();
        separatorBlockData.setFacing(leftOf(player.getFacing()));
        separatorBlock.setBlockData(separatorBlockData);
        placeSignBlock(separatorBlock, player, "[⧈] →", KEY);
    }

    default void placeBracketBlocks(Block codeBlock, Player player, Material bracketBlock) {
        Block openingBracketBlock = codeBlock.getRelative(rightOf(player.getFacing()), 1);
        openingBracketBlock.setType(bracketBlock);
        Directional openingBracketBlockData = (Directional) openingBracketBlock.getBlockData();
        openingBracketBlockData.setFacing(rightOf(player.getFacing()));
        openingBracketBlock.setBlockData(openingBracketBlockData);
        placeSignBlock(openingBracketBlock, player, "[⧈] {", KEY);

        Block closingBracketBlock = codeBlock.getRelative(rightOf(player.getFacing()), 2);
        closingBracketBlock.setType(bracketBlock);
        Directional closingBracketBlockData = (Directional) closingBracketBlock.getBlockData();
        closingBracketBlockData.setFacing(leftOf(player.getFacing()));
        closingBracketBlock.setBlockData(closingBracketBlockData);
        placeSignBlock(closingBracketBlock, player, "[⧈] }", KEY);
    }

    default void placeSignBlock(Block codeBlock, Player player, String signTitle, NamespacedKey key) {
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
        signBlockState.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        signBlockState.update();
    }

    default void placeChestBlock(Block codeBlock, Player player) {
        Block chestBlock = codeBlock.getRelative(BlockFace.UP, 1);
        chestBlock.setType(Material.CHEST);
        Directional chestBlockData = (Directional) chestBlock.getBlockData();
        chestBlockData.setFacing(player.getFacing().getOppositeFace());
        chestBlock.setBlockData(chestBlockData);
        Chest chestBlockState = (Chest) chestBlock.getState();
        chestBlockState.customName(Component.text("Coffre de paramètres"));
        chestBlockState.update();
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
