package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class FishBait extends BaseSTBItem {

    public FishBait() {}

    public FishBait(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.ROTTEN_FLESH;
    }

    @Override
    public String getItemName() {
        return "§f鱼饵";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放入 §6量子捕鱼器 §7来捕鱼", "使用 §6腐肉 §7在 §6量子发酵器 §7内制得" };
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
