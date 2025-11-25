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

public class DefineVariableCodeBlock extends ItemStack implements PluginItem, CodeBlock {
    public DefineVariableCodeBlock() {
        super(Material.WAXED_COPPER_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<gold>✘ Définir une variable");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Définit <gray>une <gold>variable <gray>qui stocke une <yellow>valeur"),
                getNonItalicComponent("<dark_gray>» <gray>et qui peut être <dark_green>réutilisée et modifiée")
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
            block.getRelative(BlockFace.UP, 1)
        );
        checkSurroundingBlocks(surroundingBlocks);

        Block separatorBlock = surroundingBlocks.getFirst();
        separatorBlock.setType(Material.STONE);

        Block signBlock = surroundingBlocks.get(1);
        signBlock.setType(Material.OAK_WALL_SIGN);
        Directional signBlockData = (Directional) signBlock.getBlockData();
        signBlockData.setFacing(player.getFacing().getOppositeFace());
        signBlock.setBlockData(signBlockData);
        Sign signBlockState = (Sign) signBlock.getState();
        signBlockState.getSide(Side.FRONT).line(0, Component.text("[⧈] ✘"));
        signBlockState.getSide(Side.FRONT).line(1, Component.text("Non défini"));
        signBlockState.getSide(Side.FRONT).setColor(DyeColor.WHITE);
        signBlockState.getSide(Side.FRONT).setGlowingText(true);
        signBlockState.setWaxed(true);
        signBlockState.update();

        Block chestBlock = surroundingBlocks.get(2);
        chestBlock.setType(Material.CHEST);
        Directional chestBlockData = (Directional) chestBlock.getBlockData();
        chestBlockData.setFacing(player.getFacing().getOppositeFace());
        chestBlock.setBlockData(chestBlockData);
    }
}
