package io.github.artyom.items;

import io.github.artyom.exceptions.NotEnoughSpaceException;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ELSECodeBlock extends ItemStack implements PluginItem, CodeBlock {
    public ELSECodeBlock() {
        super(Material.DEEPSLATE_BRICKS);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<red>≠ SINON");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>les <aqua>blocs de code <gray>à l'intérieur"),
                getNonItalicComponent("<dark_gray>» <gray>des <gold>blocs accolades <gray>si la <yellow>condition"),
                getNonItalicComponent("<dark_gray>» <gray>spécifiée est <red>fausse")
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
            block.getRelative(rightOf(player.getFacing()), 3),
            block.getRelative(player.getFacing().getOppositeFace(), 1),
            block.getRelative(BlockFace.UP, 1)
        );
        checkSurroundingBlocks(surroundingBlocks);

        Block openingBracketBlock = surroundingBlocks.getFirst();
        openingBracketBlock.setType(Material.PISTON);
        Directional openingBracketBlockData = (Directional) openingBracketBlock.getBlockData();
        openingBracketBlockData.setFacing(rightOf(player.getFacing()));
        openingBracketBlock.setBlockData(openingBracketBlockData);

        Block closingBracketBlock = surroundingBlocks.get(2);
        closingBracketBlock.setType(Material.PISTON);
        Directional closingBracketBlockData = (Directional) closingBracketBlock.getBlockData();
        closingBracketBlockData.setFacing(leftOf(player.getFacing()));
        closingBracketBlock.setBlockData(closingBracketBlockData);

        Block signBlock = surroundingBlocks.get(3);
        signBlock.setType(Material.OAK_WALL_SIGN);
        Directional signBlockData = (Directional) signBlock.getBlockData();
        signBlockData.setFacing(player.getFacing().getOppositeFace());
        signBlock.setBlockData(signBlockData);
        Sign signBlockState = (Sign) signBlock.getState();
        signBlockState.getSide(Side.FRONT).line(0, Component.text("[⧈] ≠ SINON"));
        signBlockState.getSide(Side.FRONT).setColor(DyeColor.WHITE);
        signBlockState.getSide(Side.FRONT).setGlowingText(true);
        signBlockState.setWaxed(true);
        signBlockState.update();

        Block chestBlock = surroundingBlocks.get(4);
        chestBlock.setType(Material.CHEST);
        Directional chestBlockData = (Directional) chestBlock.getBlockData();
        chestBlockData.setFacing(player.getFacing().getOppositeFace());
        chestBlock.setBlockData(chestBlockData);
    }
}
