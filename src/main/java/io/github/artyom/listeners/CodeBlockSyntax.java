package io.github.artyom.listeners;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.exceptions.ObstacleException;
import io.github.artyom.exceptions.OutsideOfWorldBorderException;
import io.github.artyom.exceptions.TooCloseToWorldBorderException;
import io.github.artyom.items.codeblocks.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
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
        ItemStack eventItem = blockPlaceEvent.getItemInHand();
        Block block = blockPlaceEvent.getBlock();
        Player player = blockPlaceEvent.getPlayer();

        CODE_BLOCKS.stream()
            .filter(eventItem::isSimilar)
            .findFirst()
            .ifPresent(codeBlock -> {
                try {
                    CodeBlock placedCodeBlock = (CodeBlock) codeBlock;
                    placedCodeBlock.onBlockPlace(block, player);
                } catch (NotEnoughSpaceException | TooCloseToWorldBorderException e) {
                    blockPlaceEvent.setCancelled(true);
                    Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize("<red>" + e.getMessage());
                    player.sendActionBar(exceptionMessage);
                }
            });
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent playerInteractEvent) {
        Action action = playerInteractEvent.getAction();
        Block clickedBlock = playerInteractEvent.getClickedBlock();
        if (action != Action.RIGHT_CLICK_BLOCK || clickedBlock == null)
            return;

        if (!(clickedBlock.getState() instanceof Sign signBlockState) ||
            !signBlockState.getPersistentDataContainer().has(CodeBlockBuilder.SEPARATOR_BLOCK_KEY))
            return;

        ItemStack eventItem = playerInteractEvent.getItem();
        if (eventItem == null || CODE_BLOCKS.stream().noneMatch(eventItem::isSimilar))
            return;

        Player player = playerInteractEvent.getPlayer();
        try {
            BlockFace playerFacing = player.getFacing();
            BlockFace rightOfPlayerFacing = CodeBlockBuilder.rightOf(playerFacing);

            Vector firstCornerDirection = new Vector(rightOfPlayerFacing.getModX(), 0, rightOfPlayerFacing.getModZ());
            Location firstCorner = clickedBlock.getLocation().clone().add(firstCornerDirection);

            int codeBlockLineLength = this.getCodeBlockLineLength(firstCorner.clone(), firstCornerDirection);
            if (codeBlockLineLength == 0)
                return;

            Vector forwardUpDirection = new Vector(playerFacing.getModX(), 1, playerFacing.getModZ());
            Vector secondCornerDirection = firstCornerDirection.clone().multiply(codeBlockLineLength).add(forwardUpDirection);
            Location secondCorner = clickedBlock.getLocation().clone().add(secondCornerDirection);

            int distance = this.getDistance(eventItem);
            this.pushCodeBlockLine(firstCorner, secondCorner, rightOfPlayerFacing, distance);
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        } catch (ObstacleException | OutsideOfWorldBorderException e) {
            Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize("<red>" + e.getMessage());
            player.sendActionBar(exceptionMessage);
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockPlaceEvent blockPlaceEvent) {

    }

    private int getCodeBlockLineLength(Location from, Vector direction) {
        Block currentBlock = from.getBlock();
        Set<NamespacedKey> namespacedKeys = Set.of(
            PlayerEventCodeBlock.KEY,
            PlayerActionCodeBlock.KEY,
            DefineVariableCodeBlock.KEY,
            IFConditionCodeBlock.KEY,
            ELSECodeBlock.KEY,
            LoopCodeBlock.KEY,
            CodeBlockBuilder.SEPARATOR_BLOCK_KEY
        );

        int codeBlockLineLength = 0;

        while (
            currentBlock.getState() instanceof Sign signBlockState &&
            namespacedKeys.stream().anyMatch(namespacedKey -> signBlockState.getPersistentDataContainer().has(namespacedKey))
        ) {
            codeBlockLineLength++;
            currentBlock = from.add(direction).getBlock();
        }

        return codeBlockLineLength;
    }

    private int getDistance(ItemStack eventItem) {
        Set<ItemStack> codeBlocksWithBracketBlocks = Set.of(
            new IFConditionCodeBlock(),
            new ELSECodeBlock(),
            new LoopCodeBlock()
        );
        if (codeBlocksWithBracketBlocks.stream().anyMatch(eventItem::isSimilar))
            return 3;
        return 2;
    }

    private void pushCodeBlockLine(Location firstCorner, Location secondCorner, BlockFace direction, int offset) throws ObstacleException, OutsideOfWorldBorderException {
        World world = firstCorner.getWorld();

        int minX = Math.min(firstCorner.getBlockX(), secondCorner.getBlockX());
        int maxX = Math.max(firstCorner.getBlockX(), secondCorner.getBlockX());
        int minY = Math.min(firstCorner.getBlockY(), secondCorner.getBlockY());
        int maxY = Math.max(firstCorner.getBlockY(), secondCorner.getBlockY());
        int minZ = Math.min(firstCorner.getBlockZ(), secondCorner.getBlockZ());
        int maxZ = Math.max(firstCorner.getBlockZ(), secondCorner.getBlockZ());

        int offsetX = direction.getModX() * offset;
        int offsetY = direction.getModY() * offset;
        int offsetZ = direction.getModZ() * offset;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location originalLocation = new Location(world, x, y, z);
                    Block originalBlock = world.getBlockAt(originalLocation);
                    if (originalBlock.getType() == Material.AIR)
                        continue;

                    Location targetLocation = originalLocation.clone().add(offsetX, offsetY, offsetZ);
                    if (!world.getWorldBorder().isInside(targetLocation))
                        throw new OutsideOfWorldBorderException();
                    Block targetBlock = world.getBlockAt(targetLocation);
                    if (targetBlock.getType() == Material.AIR)
                        continue;

                    boolean isPartOfRegion =
                        targetBlock.getX() >= minX && targetBlock.getX() <= maxX &&
                        targetBlock.getY() >= minY && targetBlock.getY() <= maxY &&
                        targetBlock.getZ() >= minZ && targetBlock.getZ() <= maxZ;
                    if (!isPartOfRegion)
                        throw new ObstacleException();
                }
            }
        }

        HashMap<Location, BlockRecord> blocksToPush = new HashMap<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location location = new Location(world, x, y, z);
                    Block block = world.getBlockAt(location);

                    if (block.getType() == Material.AIR)
                        continue;
                    blocksToPush.put(location, new BlockRecord(block.getBlockData(), block.getState()));
                }
            }
        }

        for (Location location : blocksToPush.keySet()) {
            world.getBlockAt(location).setType(Material.AIR, false);
        }

        for (Location oldLocation : blocksToPush.keySet()) {
            Location newLocation = oldLocation.clone().add(offsetX, offsetY, offsetZ);
            Block newBlock = world.getBlockAt(newLocation);

            BlockRecord oldBlockRecord = blocksToPush.get(oldLocation);
            newBlock.setType(oldBlockRecord.blockData().getMaterial());
            newBlock.setBlockData(oldBlockRecord.blockData());

            BlockState oldBlockState = oldBlockRecord.blockState();
            if (oldBlockState instanceof Sign oldSignBlockState) {
                Sign newSignBlockState = (Sign) newBlock.getState();
                for (int i = 0; i < 4; i++) {
                    newSignBlockState.getSide(Side.FRONT).line(i, oldSignBlockState.getSide(Side.FRONT).line(i));
                    newSignBlockState.getSide(Side.FRONT).setColor(oldSignBlockState.getSide(Side.FRONT).getColor());
                    newSignBlockState.getSide(Side.FRONT).setGlowingText(oldSignBlockState.getSide(Side.FRONT).isGlowingText());
                    newSignBlockState.setWaxed(oldSignBlockState.isWaxed());
                    oldSignBlockState.getPersistentDataContainer().copyTo(newSignBlockState.getPersistentDataContainer(), true);
                    newSignBlockState.update();
                }
            } else if (oldBlockState instanceof Chest oldChestBlockState) {
                Chest newChestBlockState = (Chest) newBlock.getState();
                newChestBlockState.customName(oldChestBlockState.customName());
                newChestBlockState.update();
            }
        }
    }
}
