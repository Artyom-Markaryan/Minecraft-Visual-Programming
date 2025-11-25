package io.github.artyom.items.codeblocks;

import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.items.PluginItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IFConditionCodeBlock extends ItemStack implements PluginItem, CodeBlock {
    public IFConditionCodeBlock() {
        super(Material.END_STONE_BRICKS);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<yellow>⇒ SI Condition");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>les <aqua>blocs de code <gray>à l'intérieur"),
                getNonItalicComponent("<dark_gray>» <gray>des <gold>blocs accolades <gray>si la <yellow>condition"),
                getNonItalicComponent("<dark_gray>» <gray>spécifiée est <green>vraie")
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
            block.getRelative(rightOf(player.getFacing()), 2),
            block.getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(BlockFace.UP, 1)
        );
        checkSurroundingBlocks(surroundingBlocks);

        placeBracketBlocks(block, player, Material.PISTON);
        placeSignBlock(block, player, "[⧈] ⇒");
        placeChestBlock(block, player);
    }
}
