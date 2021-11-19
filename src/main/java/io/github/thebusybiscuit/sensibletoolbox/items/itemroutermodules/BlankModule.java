package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class BlankModule extends BaseSTBItem {

    public BlankModule() {}

    public BlankModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.PAPER;
    }

    @Override
    public String getItemName() {
        return "§f空白的物品路由模块";
    }

    @Override
    public String[] getLore() {
        return new String[] { "用来 §6制作 §7其他的物品路由模块", "是 §6基础 §7的路由器模块合成材料" };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack(8));
        recipe.shape("PPP", "PRP", "PBP");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('B', Material.LAPIS_LAZULI);
        return recipe;
    }
}
