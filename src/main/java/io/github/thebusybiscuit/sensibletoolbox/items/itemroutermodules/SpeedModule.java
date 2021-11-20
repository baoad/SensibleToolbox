package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class SpeedModule extends ItemRouterModule {

    public SpeedModule() {}

    public SpeedModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §6速度升级模块";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放在 §6量子物品路由器 §7内使用", "用来 §6提高 §7路由器的传输速度", "每个升级模块可以减少 §610帧 §7的传输间隔", "最多可以叠加 §64个 §7升级模块" };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.BLAZE_POWDER);
        recipe.addIngredient(Material.EMERALD);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }
}
