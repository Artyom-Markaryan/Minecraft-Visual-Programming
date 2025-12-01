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
            CodeLocationDTO codeLocationDTO = CodeLocationDTO.fromCodeLocation(this);
            return OBJECT_MAPPER.writeValueAsString(codeLocationDTO);
        } catch (JsonProcessingException e) {
            MinecraftVisualProgramming.getInstance().getLogger().severe(
                e.getClass().getName() + " -> " + e.getMessage()
            );
            return "";
        }
    }

    public static CodeLocation deserialize(String serializedCodeLocation) {
        try {
            CodeLocationDTO codeLocationDTO = OBJECT_MAPPER.readValue(serializedCodeLocation, CodeLocationDTO.class);
            return codeLocationDTO.toCodeLocation();
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

    private record CodeLocationDTO(LocationDTO firstCorner, LocationDTO secondCorner) {
        public static CodeLocationDTO fromCodeLocation(CodeLocation codeLocation) {
            return new CodeLocationDTO(
                LocationDTO.fromLocation(codeLocation.firstCorner()),
                LocationDTO.fromLocation(codeLocation.secondCorner())
            );
        }
        public CodeLocation toCodeLocation() {
            return new CodeLocation(
                this.firstCorner.toLocation(),
                this.secondCorner.toLocation()
            );
        }
    }

    private record LocationDTO(String worldName, double x, double y, double z, float yaw, float pitch) {
        public static LocationDTO fromLocation(Location location) {
            return new LocationDTO(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
            );
        }

        public Location toLocation() {
            return new Location(
                MinecraftVisualProgramming.getInstance().getServer().getWorld(this.worldName),
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch
            );
        }
    }
}
