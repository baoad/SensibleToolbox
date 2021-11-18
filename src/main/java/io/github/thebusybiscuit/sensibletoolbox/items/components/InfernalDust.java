package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class InfernalDust extends BaseSTBItem {

    public InfernalDust() {}

    public InfernalDust(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public String getItemName() {
        return "§7地狱灰尘";
    }

    @Override
    public String[] getLore() {
        return new String[] { "击杀 §6烈焰人 §7的时候有概率掉落", "掠夺附魔可以增加掉落概率" };
    }

    @Override
    public Recipe getRecipe() {
        // no vanilla recipe to make infernal dust, but a custom recipe will be added
        return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
