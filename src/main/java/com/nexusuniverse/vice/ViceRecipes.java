package com.nexusuniverse.vice;

import com.nexusuniverse.vice.alcohol.AlcoholItems;
import com.nexusuniverse.vice.alcohol.AlcoholType;
import com.nexusuniverse.vice.substances.Substance;
import com.nexusuniverse.vice.substances.SubstanceItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public final class ViceRecipes {

    private ViceRecipes() {}

    public static void registerAll(Plugin plugin, SubstanceItems substanceItems, AlcoholItems alcoholItems) {
        registerSubstance(plugin, substanceItems, Substance.FENTINOLI, Material.SUGAR, Material.BLAZE_POWDER);
        registerSubstance(plugin, substanceItems, Substance.XANAXEL, Material.SUGAR, Material.REDSTONE);
        registerSubstance(plugin, substanceItems, Substance.OPIATRIX, Material.SUGAR, Material.POPPY);
        registerSubstance(plugin, substanceItems, Substance.MOLOTINE, Material.SUGAR, Material.GLOWSTONE_DUST);
        registerSubstance(plugin, substanceItems, Substance.COCAINIUM, Material.SUGAR, Material.SNOWBALL);
        registerSubstance(plugin, substanceItems, Substance.MOLLYQ, Material.SUGAR, Material.PINK_DYE);
        registerSubstance(plugin, substanceItems, Substance.ACIDROP, Material.SUGAR, Material.FERMENTED_SPIDER_EYE);
        registerSubstance(plugin, substanceItems, Substance.HERBALIS, Material.SUGAR, Material.NETHER_WART);

        registerAlcohol(plugin, alcoholItems, AlcoholType.BEER, Material.WHEAT, Material.WHEAT);
        registerAlcohol(plugin, alcoholItems, AlcoholType.WINE, Material.SWEET_BERRIES, Material.SUGAR);
        registerAlcohol(plugin, alcoholItems, AlcoholType.LIQUOR, Material.POTATO, Material.SUGAR);
    }

    private static void registerSubstance(Plugin plugin, SubstanceItems items, Substance substance, Material a, Material b) {
        NamespacedKey key = new NamespacedKey(plugin, "vice_" + substance.configKey());
        ItemStack result = items.create(substance);

        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.GLASS_BOTTLE));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(a));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(b));
        Bukkit.addRecipe(recipe);
    }

    private static void registerAlcohol(Plugin plugin, AlcoholItems items, AlcoholType type, Material a, Material b) {
        NamespacedKey key = new NamespacedKey(plugin, "vice_alcohol_" + type.configKey());
        ItemStack result = items.create(type);

        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.GLASS_BOTTLE));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(a));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(b));
        Bukkit.addRecipe(recipe);
    }
}
