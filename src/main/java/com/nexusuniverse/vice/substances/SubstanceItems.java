package com.nexusuniverse.vice.substances;

import com.nexusuniverse.vice.ViceConfig;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

import java.util.List;

public class SubstanceItems {

    private final ViceConfig config;
    private final NamespacedKey substanceKey;

    public SubstanceItems(Plugin plugin, ViceConfig config) {
        this.config = config;
        this.substanceKey = new NamespacedKey(plugin, "vice_substance");
    }

    public ItemStack create(Substance substance) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        meta.setBasePotionType(PotionType.WATER); // a plain, colorless base -- setColor() below does the actual tinting
        meta.setDisplayName(colorFor(substance) + config.displayName(substance));
        meta.setLore(List.of("§7Right-click to use.", "§8Effects vary with how much you take."));
        meta.setColor(tintFor(substance));
        meta.getPersistentDataContainer().set(substanceKey, PersistentDataType.STRING, substance.name());

        item.setItemMeta(meta);
        return item;
    }

    /** Returns the substance this item represents, or null if it isn't one of ours. */
    public Substance readSubstance(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String raw = item.getItemMeta().getPersistentDataContainer().get(substanceKey, PersistentDataType.STRING);
        if (raw == null) return null;
        try {
            return Substance.valueOf(raw);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String colorFor(Substance substance) {
        return switch (substance.category()) {
            case DEPRESSANT -> "§9";
            case STIMULANT -> "§c";
            case HALLUCINOGEN -> "§d";
            case MELLOW -> "§a";
        };
    }

    private Color tintFor(Substance substance) {
        return switch (substance) {
            case FENTINOLI -> Color.fromRGB(40, 40, 80);
            case XANAXEL -> Color.fromRGB(100, 140, 220);
            case OPIATRIX -> Color.fromRGB(70, 70, 120);
            case MOLOTINE -> Color.fromRGB(220, 30, 30);
            case COCAINIUM -> Color.fromRGB(240, 240, 240);
            case MOLLYQ -> Color.fromRGB(230, 60, 180);
            case ACIDROP -> Color.fromRGB(150, 220, 60);
            case HERBALIS -> Color.fromRGB(50, 140, 50);
        };
    }
}
