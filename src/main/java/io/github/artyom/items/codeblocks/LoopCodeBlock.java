package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    public void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException {
        List<Block> surroundingBlocks = List.of(
            block.getRelative(CodeBlockBuilder.rightOf(player.getFacing()), 1),
            block.getRelative(CodeBlockBuilder.rightOf(player.getFacing()), 2),
            block.getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(CodeBlockBuilder.rightOf(player.getFacing()), 1).getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(CodeBlockBuilder.rightOf(player.getFacing()), 2).getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(BlockFace.UP, 1)
        );
        CodeBlockBuilder.checkSurroundingBlocks(surroundingBlocks);

        CodeBlockBuilder.placeBracketBlocks(block, player, Material.STICKY_PISTON);
        CodeBlockBuilder.placeSignBlock(block, player, "[⧈] ∑", KEY);
        CodeBlockBuilder.placeChestBlock(block, player);
    }
}
