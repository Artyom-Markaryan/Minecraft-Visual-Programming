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

public class PlayerEventCodeBlock extends ItemStack implements CodeBlock {
    public static final NamespacedKey KEY = new NamespacedKey(MinecraftVisualProgramming.getInstance(), "PlayerEventCodeBlock");

    public PlayerEventCodeBlock() {
        super(Material.DIAMOND_BLOCK);

        ItemMeta itemMeta = super.getItemMeta();
        Component customName = ServerItem.getNonItalicComponent("<dark_aqua>⚡ Événement du joueur");
        itemMeta.customName(customName);
        itemMeta.lore(
            List.of(
                ServerItem.getNonItalicComponent("<dark_gray>» <blue>Exécute <gray>une ligne des <aqua>blocs de code <gray>quand"),
                ServerItem.getNonItalicComponent("<dark_gray>» <gray>le <dark_aqua>joueur <gray>déclenche <dark_purple>l'événement <gray>spécifié")
            )
        );
        itemMeta.setEnchantmentGlintOverride(true);
        itemMeta.setMaxStackSize(1);
        super.setItemMeta(itemMeta);
    }

    @Override
    public void onBlockPlace(Block block, Player player) throws NotEnoughSpaceException, TooCloseToWorldBorderException {
        CodeBlockBuilder.createInstance(block, player)
            .setSignTitle("[⧈] ⚡ JOUEUR")
            .setNamespacedKey(KEY)
            .build();
    }
}
