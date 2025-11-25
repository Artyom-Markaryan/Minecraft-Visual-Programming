package io.github.artyom.items;

import io.github.artyom.exceptions.NotEnoughSpaceException;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerEventCodeBlock extends ItemStack implements PluginItem, CodeBlock {
    public PlayerEventCodeBlock() {
        super(Material.DIAMOND_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = getNonItalicComponent("<dark_aqua>→ Événement du joueur");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>une ligne des <aqua>blocs de code <gray>lorsque"),
                getNonItalicComponent("<dark_gray>» <gray>le <light_purple>joueur <dark_purple>déclenche l'événement <gray>spécifié")
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
            block.getRelative(player.getFacing().getOppositeFace(), 1)
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
        signBlockState.getSide(Side.FRONT).line(0, Component.text("[⧈] → JOUEUR"));
        signBlockState.getSide(Side.FRONT).line(1, Component.text("Non défini"));
        signBlockState.getSide(Side.FRONT).setColor(DyeColor.WHITE);
        signBlockState.getSide(Side.FRONT).setGlowingText(true);
        signBlockState.setWaxed(true);
        signBlockState.update();
    }
}
