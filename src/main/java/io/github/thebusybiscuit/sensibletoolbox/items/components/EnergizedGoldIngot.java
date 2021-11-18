package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class EnergizedGoldIngot extends BaseSTBItem {

    public EnergizedGoldIngot() {}

    public EnergizedGoldIngot(ConfigurationSection conf) {

    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_INGOT;
    }

    @Override
    public String getItemName() {
        return "§d充电的金锭";
    }

    @Override
    public String[] getLore() {
        return new String[] { "电子不安的涌动,发出诡异的光芒..." };
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
