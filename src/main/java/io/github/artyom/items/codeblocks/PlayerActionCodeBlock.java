package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.items.PluginItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerActionCodeBlock extends ItemStack implements PluginItem, CodeBlock {
    private static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "PlayerActionCodeBlock");

    public PlayerActionCodeBlock() {
        super(Material.DIAMOND_ORE);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<dark_aqua>∀ Action du joueur");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Effectue <gray>une <dark_purple>action <gray>liée au <light_purple>joueur")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }

    @Override
    public void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException {
        List<Block> surroundingBlocks = List.of(
            block.getRelative(rightOf(player.getFacing()), 1),
            block.getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(rightOf(player.getFacing()), 1).getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(BlockFace.UP, 1)
        );
        checkSurroundingBlocks(surroundingBlocks);

        placeSeparatorBlock(block, player);
        placeSignBlock(block, player, "[⧈] ∀ JOUEUR", KEY);
        placeChestBlock(block, player);
    }
}
