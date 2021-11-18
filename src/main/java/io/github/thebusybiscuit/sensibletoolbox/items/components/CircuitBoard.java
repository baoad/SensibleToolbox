package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class CircuitBoard extends BaseSTBItem {

    public CircuitBoard() {}

    public CircuitBoard(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.GREEN_CARPET;
    }

    @Override
    public String getItemName() {
        return "§e电路主板";
    }

    @Override
    public String[] getLore() {
        return new String[] { "是制造其他 §6电子设备 §7的原材料" };
    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack(2));
        recipe.addIngredient(Material.STONE_PRESSURE_PLATE);
        recipe.addIngredient(Material.GREEN_DYE);
        return recipe;
    }
}
