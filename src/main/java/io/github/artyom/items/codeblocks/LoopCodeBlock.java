package io.github.artyom.items.codeblocks;

import io.github.artyom.MinecraftVisualProgramming;
import io.github.artyom.exceptions.NotEnoughSpaceException;
import io.github.artyom.exceptions.TooCloseToWorldBorderException;
import io.github.artyom.items.ServerItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LoopCodeBlock extends ItemStack implements CodeBlock {
    public static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "LoopCodeBlock");

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
        CodeBlockBuilder.createInstance(block, player)
            .setSignTitle("[⧈] ∑")
            .setNamespacedKey(KEY)
            .placeBracketBlocks(Material.PISTON)
            .placeChestBlock()
            .build();
    }
}
