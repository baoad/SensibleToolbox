package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class StackModule extends ItemRouterModule {

    public StackModule() {}

    public StackModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §6容量升级模块";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放在 §6量子物品路由器 §7内使用", "每个 §6容量升级模块 §7都可以使得", "每次运行时处理的 §6物品数量 §7翻倍", "最多可以堆叠 §66个 §7容量升级模块" };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.BRICK);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.BRICK;
    }
}
