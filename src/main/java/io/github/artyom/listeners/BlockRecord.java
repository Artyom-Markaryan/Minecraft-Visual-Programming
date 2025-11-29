package io.github.artyom.listeners;

import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

public record BlockRecord(BlockData blockData, BlockState blockState) {}
