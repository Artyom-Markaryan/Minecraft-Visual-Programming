package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ELSECodeBlock extends ItemStack implements CodeBlock {
    private static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "ELSECodeBlock");

    public ELSECodeBlock() {
        super(Material.DEEPSLATE_BRICKS);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<red>≠ SINON");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>les <aqua>blocs de code <gray>à l'intérieur"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>des <gold>blocs accolades <gray>si la <yellow>condition"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>spécifiée est <red>fausse")
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
            block.getRelative(CodeBlockBuilder.rightOf(player.getFacing()), 2).getRelative(player.getFacing().getOppositeFace(), 1)
        );
        CodeBlockBuilder.checkSurroundingBlocks(surroundingBlocks);

        CodeBlockBuilder.placeBracketBlocks(block, player, Material.PISTON);
        CodeBlockBuilder.placeSignBlock(block, player, "[⧈] ≠", KEY);
    }
}
