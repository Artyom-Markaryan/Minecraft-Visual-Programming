package io.github.artyom.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.artyom.MinecraftVisualProgramming;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public record CodeLocation(Location firstCorner, Location secondCorner) {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public @NotNull String toString() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            MinecraftVisualProgramming.getInstance().getLogger().severe(
                e.getClass().getName() + " -> " + e.getMessage()
            );
            return "";
        }
    }

    public static CodeLocation deserialize(String serializedCodeLocation) {
        try {
            return OBJECT_MAPPER.readValue(serializedCodeLocation, CodeLocation.class);
        } catch (JsonProcessingException e) {
            MinecraftVisualProgramming.getInstance().getLogger().severe(
                e.getClass().getName() + " -> " + e.getMessage()
            );
            return null;
        }
    }

    public int[] getSize() {
        int minX = Math.min(this.firstCorner.getBlockX(), this.secondCorner.getBlockX());
        int maxX = Math.max(this.firstCorner.getBlockX(), this.secondCorner.getBlockX());
        int minY = Math.min(this.firstCorner.getBlockY(), this.secondCorner.getBlockY());
        int maxY = Math.max(this.firstCorner.getBlockY(), this.secondCorner.getBlockY());
        int minZ = Math.min(this.firstCorner.getBlockZ(), this.secondCorner.getBlockZ());
        int maxZ = Math.max(this.firstCorner.getBlockZ(), this.secondCorner.getBlockZ());

        int codeLocationWidth = 0;
        int codeLocationHeight = 0;
        int codeLocationDepth = 0;

        for (int x = minX; x <= maxX; x++) {
            codeLocationWidth++;
        }
        for (int y = minY; y <= maxY; y++) {
            codeLocationHeight++;
        }
        for (int z = minZ; z <= maxZ; z++) {
            codeLocationDepth++;
        }

        return new int[]{codeLocationWidth, codeLocationHeight, codeLocationDepth};
    }
}
