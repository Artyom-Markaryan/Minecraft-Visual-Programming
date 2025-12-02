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

import java.util.ArrayList;
import java.util.List;

public final class CodeBlockBuilder {
    public static final NamespacedKey SEPARATOR_BLOCK_KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "SeparatorBlock");

    private final Location codeBlockLocation;
    private final Player player;

    private final Vector separatorBlockDirection;
    private final Vector chestBlockDirection;
    private final Vector signBlockDirection;

    private String signTitle;
    private NamespacedKey namespacedKey;

    private boolean needsBracketBlocks = false;
    private boolean needsChestBlock = false;

    private Material bracketBlockType;

    private CodeBlockBuilder(Block codeBlock, Player player) {
        this.codeBlockLocation = codeBlock.getLocation();
        this.player = player;

        this.separatorBlockDirection = new Vector(rightOf(player.getFacing()).getModX(), 0, rightOf(player.getFacing()).getModZ());
        this.chestBlockDirection = new Vector(0, BlockFace.UP.getModY(), 0);
        this.signBlockDirection = new Vector(player.getFacing().getOppositeFace().getModX(), 0, player.getFacing().getOppositeFace().getModZ());
    }

    public static CodeBlockBuilder createInstance(Block block, Player player) {
        return new CodeBlockBuilder(block, player);
    }

    public CodeBlockBuilder setSignTitle(String signTitle) {
        this.signTitle = signTitle;
        return this;
    }

    public CodeBlockBuilder setNamespacedKey(NamespacedKey namespacedKey) {
        this.namespacedKey = namespacedKey;
        return this;
    }

    public CodeBlockBuilder placeBracketBlocks(Material bracketBlock) {
        this.needsBracketBlocks = true;
        this.bracketBlockType = bracketBlock;
        return this;
    }

    public CodeBlockBuilder placeChestBlock() {
        this.needsChestBlock = true;
        return this;
    }

    public void build() throws NotEnoughSpaceException, TooCloseToWorldBorderException {
        World world = player.getWorld();

        List<Location> surroundingLocations = new ArrayList<>();

        Location separatorBlockLocation = this.codeBlockLocation.clone().add(this.separatorBlockDirection);
        surroundingLocations.add(separatorBlockLocation);
        Location signBlockLocation = this.codeBlockLocation.clone().add(this.signBlockDirection);
        surroundingLocations.add(signBlockLocation);
        Location separatorBlockSignLocation = separatorBlockLocation.clone().add(this.signBlockDirection);
        surroundingLocations.add(separatorBlockSignLocation);

        if (this.needsBracketBlocks) {
            Vector closingBracketBlockDirection = this.separatorBlockDirection.clone().multiply(2);
            Location closingBracketBlockLocation = this.codeBlockLocation.clone().add(closingBracketBlockDirection);
            surroundingLocations.add(closingBracketBlockLocation);
            Location closingBracketBlockSignLocation = closingBracketBlockLocation.clone().add(this.signBlockDirection);
            surroundingLocations.add(closingBracketBlockSignLocation);
        }

        if (this.needsChestBlock) {
            Location chestBlockLocation = this.codeBlockLocation.clone().add(this.chestBlockDirection);
            surroundingLocations.add(chestBlockLocation);
        }

        checkSurroundingLocations(world, surroundingLocations);

        placeSignBlock(surroundingLocations.get(1), this.player, this.signTitle, namespacedKey);

        if (this.needsBracketBlocks)
            this.placeBracketBlocks(new BracketBlockLocations(surroundingLocations.getFirst(), surroundingLocations.get(3)), this.player, this.bracketBlockType);
        else
            this.placeSeparatorBlock(surroundingLocations.getFirst(), this.player);

        if (this.needsChestBlock)
            this.placeChestBlock(surroundingLocations.getLast(), this.player);
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

    private void checkSurroundingLocations(World world, List<Location> surroundingLocations) throws NotEnoughSpaceException, TooCloseToWorldBorderException {
        if (surroundingLocations.stream().anyMatch(location -> location.getBlock().getType() != Material.AIR))
            throw new NotEnoughSpaceException();

        if (surroundingLocations.stream().anyMatch(location -> !world.getWorldBorder().isInside(location)))
            throw new TooCloseToWorldBorderException();
    }

    private void placeSeparatorBlock(Location location, Player player) {
        Block separatorBlock = location.getBlock();
        separatorBlock.setType(Material.MOSSY_COBBLESTONE);

        placeSignBlock(location.add(this.signBlockDirection), player, "[⧈] →", SEPARATOR_BLOCK_KEY);
    }

    private void placeBracketBlocks(BracketBlockLocations bracketBlockLocations, Player player, Material bracketBlockType) {
        for (int i = 0; i <= 1; i++) {
            Location bracketBlockLocation = i == 0 ? bracketBlockLocations.opening() : bracketBlockLocations.closing();
            Block bracketBlock = bracketBlockLocation.getBlock();
            bracketBlock.setType(bracketBlockType);

            Directional bracketBlockBlockData = (Directional) bracketBlock.getBlockData();
            BlockFace facing = i == 0 ? rightOf(player.getFacing()) : leftOf(player.getFacing());
            bracketBlockBlockData.setFacing(facing);
            bracketBlock.setBlockData(bracketBlockBlockData);

            String signTitle = i == 0 ? "[⧈] {" : "[⧈] }";
            placeSignBlock(bracketBlockLocation.add(this.signBlockDirection), player, signTitle, SEPARATOR_BLOCK_KEY);
        }
    }

    private void placeSignBlock(Location location, Player player, String signTitle, NamespacedKey key) {
        Block signBlock = location.clone().getBlock();
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

    private void placeChestBlock(Location location, Player player) {
        Block chestBlock = location.getBlock();
        chestBlock.setType(Material.CHEST);

        Directional chestBlockData = (Directional) chestBlock.getBlockData();
        chestBlockData.setFacing(player.getFacing().getOppositeFace());
        chestBlock.setBlockData(chestBlockData);

        Chest chestBlockState = (Chest) chestBlock.getState();
        chestBlockState.customName(Component.text("Coffre de paramètres"));
        chestBlockState.update();
    }
}
