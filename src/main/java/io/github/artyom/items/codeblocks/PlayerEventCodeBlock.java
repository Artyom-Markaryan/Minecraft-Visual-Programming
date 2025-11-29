package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.exceptions.TooCloseToWorldBorderException;
import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class PlayerEventCodeBlock extends ItemStack implements CodeBlock {
    private static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "PlayerEventCodeBlock");

    public PlayerEventCodeBlock() {
        super(Material.DIAMOND_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<dark_aqua>⚡ Événement du joueur");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>une ligne des <aqua>blocs de code <gray>lorsque"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>le <light_purple>joueur <dark_purple>déclenche l'événement <gray>spécifié")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }

    @Override
    public void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException, TooCloseToWorldBorderException {
        World world = player.getWorld();

        Location codeBlockLocation = block.getLocation();

        Vector separatorBlockDirection = new Vector(CodeBlockBuilder.rightOf(player.getFacing()).getModX(), 0, CodeBlockBuilder.rightOf(player.getFacing()).getModZ());
        Vector codeBlockSignDirection = new Vector(player.getFacing().getOppositeFace().getModX(), 0, player.getFacing().getOppositeFace().getModZ());
        Vector separatorBlockSignDirection = new Vector(separatorBlockDirection.getX() + codeBlockSignDirection.getX(), 0, separatorBlockDirection.getZ() + codeBlockSignDirection.getZ());

        List<Location> surroundingLocations = List.of(
            codeBlockLocation.clone().add(separatorBlockDirection),
            codeBlockLocation.clone().add(codeBlockSignDirection),
            codeBlockLocation.clone().add(separatorBlockSignDirection)
        );
        CodeBlockBuilder.checkSurroundingLocations(world, surroundingLocations);

        CodeBlockBuilder.placeSeparatorBlock(surroundingLocations.getFirst(), player);
        CodeBlockBuilder.placeSignBlock(codeBlockLocation, player, "[⧈] ⚡ JOUEUR", KEY);
    }
}
