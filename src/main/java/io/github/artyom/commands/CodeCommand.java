package io.github.artyom.commands;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.IncorrectCodeLocationSizeException;
import io.github.artyom.exceptions.OutsideOfWorldBorderException;
import io.github.artyom.items.CodeBlocksItem;
import io.github.artyom.items.ValuesItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CodeCommand implements CommandExecutor, TabCompleter {
    List<String> options = List.of("location", "edit", "compile");
    public static final NamespacedKey CODE_LOCATION_KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "CodeLocation");

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String @NotNull [] args
    ) {
        if (!(sender instanceof Player player)) {
            Component errorMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                "<red>Cette commande ne peut être exécutée que par un joueur!"
            );
            sender.sendMessage(errorMessage);
            return true;
        }

        if (args.length < 1) {
            Component errorMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                "<red>Merci de spécifier l'une des options suivantes: <#0BDA51>/code location<red>, <#0BDA51>/code edit<red>, ou <#0BDA51>/code compile<red>."
            );
            player.sendMessage(errorMessage);
            return true;
        } else if (args[0].equalsIgnoreCase(options.getFirst()) && args.length < 7) {
            Component errorMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                "<red>Syntaxe attendue: <#0BDA51>/code location <green>x₁ y₁ z₁ <dark_green>x₂ y₂ z₂"
            );
            player.sendMessage(errorMessage);
            return true;
        }

        if (args[0].equalsIgnoreCase(options.getFirst())) {
            try {
                World world = player.getWorld();
                Location firstCorner = this.parseLocation(world, new String[]{args[1], args[2], args[3]});
                Location secondCorner = this.parseLocation(world, new String[]{args[4], args[5], args[6]});
                CodeLocation codeLocation = new CodeLocation(firstCorner, secondCorner);
                this.setCodeLocation(codeLocation, player);
                Component successMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                    "<#0BDA51>L'emplacement de ton code a été correctement défini!"
                );
                player.sendMessage(successMessage);
            } catch (NumberFormatException e) {
                Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                    "<red>Les coordonnées de l'emplacement de ton code doivent être des nombres!"
                );
                player.sendMessage(exceptionMessage);
            } catch (IncorrectCodeLocationSizeException | OutsideOfWorldBorderException e) {
                Component exceptionMessage = MinecraftVisualProgramming.MINI_MESSAGE.deserialize(
                    "<red>" + e.getMessage()
                );
                player.sendMessage(exceptionMessage);
            }
        } else if (args[0].equalsIgnoreCase(options.get(1))) {
            this.setCodeInventory(player);
        } else if (args[0].equalsIgnoreCase(options.get(2))) {
            this.compileCode();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String @NotNull [] args
    ) {
        if (args.length == 1) return options;
        if (args[0].equalsIgnoreCase(options.getFirst()) && args.length <= 7) {
            List<String> locationOptionArguments = List.of("x₁", "y₁", "z₁", "x₂", "y₂", "z₂");
            return List.of(locationOptionArguments.get(args.length - 2));
        }
        return List.of();
    }

    private Location parseLocation(World world, String[] coordinates) throws OutsideOfWorldBorderException {
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);
        double z = Double.parseDouble(coordinates[2]);

        Location parsedLocation = new Location(world, x, y, z);
        if (!world.getWorldBorder().isInside(parsedLocation))
            throw new OutsideOfWorldBorderException(
                "L'emplacement de ton code ne peut pas être à l'extérieur de la bordure du monde!"
            );
        return parsedLocation;
    }

    private void setCodeLocation(CodeLocation codeLocation, Player player) throws IncorrectCodeLocationSizeException {
        this.checkCodeLocationSize(codeLocation.getSize());
        player.getPersistentDataContainer().set(CODE_LOCATION_KEY, PersistentDataType.STRING, codeLocation.toString());
        String serializedCodeLocation = codeLocation.toString();
        player.sendMessage(Component.text("Ton emplacement de code est: " + serializedCodeLocation));
    }

    private void checkCodeLocationSize(int[] codeLocationSize) throws IncorrectCodeLocationSizeException {
        if (codeLocationSize[0] < 10 || codeLocationSize[1] < 2 || codeLocationSize[2] < 10) {
            throw new IncorrectCodeLocationSizeException("L'emplacement de ton code ne peut pas être plus petit que 10x2x10 blocs!");
        } else if (codeLocationSize[0] > 100 || codeLocationSize[1] > 100 || codeLocationSize[2] > 100) {
            throw new IncorrectCodeLocationSizeException("L'emplacement de ton code ne peut pas être plus grand que 100x100x100 blocs!");
        }
    }

    private void setCodeInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(7, new CodeBlocksItem());
        player.getInventory().setItem(8, new ValuesItem());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

    private void compileCode() {
        // TODO: À implémenter...
    }
}
