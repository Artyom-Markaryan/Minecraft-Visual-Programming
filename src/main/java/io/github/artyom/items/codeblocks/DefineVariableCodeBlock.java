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

public class DefineVariableCodeBlock extends ItemStack implements CodeBlock {
    private static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "DefineVariableCodeBlock");

    public DefineVariableCodeBlock() {
        super(Material.WAXED_COPPER_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<gold>✘ Définir une variable");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Définit <gray>une <gold>variable <gray>qui stocke une <yellow>valeur"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>et qui peut être <dark_green>réutilisée et modifiée")
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
            block.getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(CodeBlockBuilder.rightOf(player.getFacing()), 1).getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(BlockFace.UP, 1)
        );
        CodeBlockBuilder.checkSurroundingBlocks(surroundingBlocks);

        CodeBlockBuilder.placeSeparatorBlock(block, player);
        CodeBlockBuilder.placeSignBlock(block, player, "[⧈] ✘", KEY);
        CodeBlockBuilder.placeChestBlock(block, player);
    }
}
