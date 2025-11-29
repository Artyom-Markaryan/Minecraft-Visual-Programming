package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.exceptions.TooCloseToWorldBorderException;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.List;

public final class CodeBlockBuilder {
    public static final NamespacedKey SEPARATOR_BLOCK_KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "SeparatorBlock");

    public static void placeSeparatorBlock(Location location, Player player) {
        Block separatorBlock = location.getBlock();
        separatorBlock.setType(Material.OBSERVER);

        Directional separatorBlockData = (Directional) separatorBlock.getBlockData();
        separatorBlockData.setFacing(leftOf(player.getFacing()));
        separatorBlock.setBlockData(separatorBlockData);

        placeSignBlock(location, player, "[⧈] →", SEPARATOR_BLOCK_KEY);
    }

    public static void placeBracketBlocks(List<Location> locations, Player player, Material bracketBlock) {
        Block openingBracketBlock = locations.getFirst().getBlock();
        openingBracketBlock.setType(bracketBlock);

        Directional openingBracketBlockData = (Directional) openingBracketBlock.getBlockData();
        openingBracketBlockData.setFacing(rightOf(player.getFacing()));
        openingBracketBlock.setBlockData(openingBracketBlockData);

        placeSignBlock(locations.getFirst(), player, "[⧈] {", SEPARATOR_BLOCK_KEY);

        Block closingBracketBlock = locations.get(1).getBlock();
        closingBracketBlock.setType(bracketBlock);

        Directional closingBracketBlockData = (Directional) closingBracketBlock.getBlockData();
        closingBracketBlockData.setFacing(leftOf(player.getFacing()));
        closingBracketBlock.setBlockData(closingBracketBlockData);

        placeSignBlock(locations.get(1), player, "[⧈] }", SEPARATOR_BLOCK_KEY);
    }

    public static void placeSignBlock(Location location, Player player, String signTitle, NamespacedKey key) {
        Vector signBlockDirection = new Vector(player.getFacing().getOppositeFace().getModX(), 0, player.getFacing().getOppositeFace().getModZ());
        Block signBlock = location.clone().add(signBlockDirection).getBlock();
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

    public static void placeChestBlock(Location location, Player player) {
        Block chestBlock = location.getBlock();
        chestBlock.setType(Material.CHEST);

        Directional chestBlockData = (Directional) chestBlock.getBlockData();
        chestBlockData.setFacing(player.getFacing().getOppositeFace());
        chestBlock.setBlockData(chestBlockData);

        Chest chestBlockState = (Chest) chestBlock.getState();
        chestBlockState.customName(Component.text("Coffre de paramètres"));
        chestBlockState.update();
    }

    public static BlockFace rightOf(BlockFace cardinalDirection) {
        return switch (cardinalDirection) {
            case NORTH -> BlockFace.EAST;
            case EAST -> BlockFace.SOUTH;
            case SOUTH -> BlockFace.WEST;
            case WEST -> BlockFace.NORTH;
            default -> BlockFace.SELF;
        };
    }

    public static BlockFace leftOf(BlockFace cardinalDirection) {
        return switch (cardinalDirection) {
            case NORTH -> BlockFace.WEST;
            case EAST -> BlockFace.NORTH;
            case SOUTH -> BlockFace.EAST;
            case WEST -> BlockFace.SOUTH;
            default -> BlockFace.SELF;
        };
    }

    public static BlockFace diagonalRightOf(BlockFace cardinalDirection) {
        return switch (cardinalDirection) {
            case NORTH -> BlockFace.NORTH_EAST;
            case EAST -> BlockFace.SOUTH_EAST;
            case SOUTH -> BlockFace.SOUTH_WEST;
            case WEST -> BlockFace.NORTH_WEST;
            default -> BlockFace.SELF;
        };
    }

    public static void checkSurroundingLocations(World world, List<Location> surroundingLocations) throws NotEnoughSpaceException, TooCloseToWorldBorderException {
        if (surroundingLocations.stream().anyMatch(location -> location.getBlock().getType() != Material.AIR))
            throw new NotEnoughSpaceException();

        if (surroundingLocations.stream().anyMatch(location -> !world.getWorldBorder().isInside(location)))
            throw new TooCloseToWorldBorderException();
    }
}
