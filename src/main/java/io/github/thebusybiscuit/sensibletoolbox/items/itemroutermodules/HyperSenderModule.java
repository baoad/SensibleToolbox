package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.items.components.SubspaceTransponder;
import io.github.thebusybiscuit.sensibletoolbox.utils.UnicodeSymbol;

public class HyperSenderModule extends AdvancedSenderModule {

    public HyperSenderModule() {
        super();
    }

    public HyperSenderModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.CYAN_DYE;
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b超级传送器:";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放在 §6量子物品路由器 §7内使用", "用于传送路由器 §6缓冲区 §7内的物品", "用于传送路由器 §6缓冲区 §7内的物品", "到链接的放有 §6接收器 §7的路由器内", "", "左键: 装有 §6接收器 §7的路由器来安装配置", "蹲下 + 左键: 来 §6取消 §7链接" };
    }

    @Override
    public Recipe getRecipe() {
        SenderModule sm = new SenderModule();
        SubspaceTransponder st = new SubspaceTransponder();
        registerCustomIngredients(sm, st);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(sm.getMaterial());
        recipe.addIngredient(st.getMaterial());
        return recipe;
    }

    @Override
    protected boolean inRange(Location ourLoc) {
        return ourLoc != null;
    }
}
