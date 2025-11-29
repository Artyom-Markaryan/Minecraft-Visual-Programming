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
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class LoopCodeBlock extends ItemStack implements CodeBlock {
    private static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "LoopCodeBlock");

    public LoopCodeBlock() {
        super(Material.PRISMARINE);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<aqua>∑ Boucle");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Répète l'exécution <gray>des <aqua>blocs de code"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>à l'intérieur des <gold>blocs accolades"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>selon la <blue>façon <gray>spécifiée")
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

        Vector bracketBlockDirection = new Vector(CodeBlockBuilder.rightOf(player.getFacing()).getModX(), 0, CodeBlockBuilder.rightOf(player.getFacing()).getModZ());
        Vector codeBlockSignDirection = new Vector(player.getFacing().getOppositeFace().getModX(), 0, player.getFacing().getOppositeFace().getModZ());
        Vector bracketBlockSignDirection = new Vector(bracketBlockDirection.getX() + codeBlockSignDirection.getX(), 0, bracketBlockDirection.getZ() + codeBlockSignDirection.getZ());
        Vector chestBlockDirection = new Vector(0, BlockFace.UP.getModY(), 0);

        List<Location> surroundingLocations = List.of(
            codeBlockLocation.clone().add(bracketBlockDirection),
            codeBlockLocation.clone().add(bracketBlockDirection.clone().multiply(2)),
            codeBlockLocation.clone().add(codeBlockSignDirection),
            codeBlockLocation.clone().add(bracketBlockSignDirection),
            codeBlockLocation.clone().add(bracketBlockSignDirection.clone().add(bracketBlockDirection)),
            codeBlockLocation.clone().add(chestBlockDirection)
        );
        CodeBlockBuilder.checkSurroundingLocations(world, surroundingLocations);

        CodeBlockBuilder.placeBracketBlocks(List.of(surroundingLocations.getFirst(), surroundingLocations.get(1)), player, Material.STICKY_PISTON);
        CodeBlockBuilder.placeSignBlock(codeBlockLocation, player, "[⧈] ∑", KEY);
        CodeBlockBuilder.placeChestBlock(surroundingLocations.getLast(), player);
    }
}
