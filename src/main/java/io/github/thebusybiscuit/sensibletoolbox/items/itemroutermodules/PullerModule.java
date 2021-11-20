package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class PullerModule extends DirectionalItemRouterModule {

    public PullerModule() {}

    public PullerModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b吸引器";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore("放在 §6量子物品路由器 §7内使用", "用来从相邻的容器内 §6抽取 §7物品", "到所在路由器的物品 §6缓冲区 §7内", "", "左键方块: §6设置方向", "蹲下 + 左键空气: §6删除方向");
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.STICKY_PISTON);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.LIME_DYE;
    }

    @Override
    public boolean execute(Location loc) {
        return getItemRouter() != null && doPull(getFacing(), loc);
    }
}
