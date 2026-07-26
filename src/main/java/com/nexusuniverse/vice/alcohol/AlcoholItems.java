package com.nexusuniverse.vice.alcohol;

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

public class AlcoholItems {

    private final ViceConfig config;
    private final NamespacedKey alcoholKey;

    public AlcoholItems(Plugin plugin, ViceConfig config) {
        this.config = config;
        this.alcoholKey = new NamespacedKey(plugin, "vice_alcohol");
    }

    public ItemStack create(AlcoholType type) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        meta.setBasePotionType(PotionType.WATER);
        meta.setDisplayName("§6" + config.displayName(type));
        meta.setLore(List.of("§7Right-click to drink.", "§8The more you have, the worse it gets."));
        meta.setColor(tintFor(type));
        meta.getPersistentDataContainer().set(alcoholKey, PersistentDataType.STRING, type.name());

        item.setItemMeta(meta);
        return item;
    }

    public AlcoholType readType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String raw = item.getItemMeta().getPersistentDataContainer().get(alcoholKey, PersistentDataType.STRING);
        if (raw == null) return null;
        try {
            return AlcoholType.valueOf(raw);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Color tintFor(AlcoholType type) {
        return switch (type) {
            case BEER -> Color.fromRGB(212, 155, 45);
            case WINE -> Color.fromRGB(115, 20, 45);
            case LIQUOR -> Color.fromRGB(230, 210, 150);
        };
    }
}
