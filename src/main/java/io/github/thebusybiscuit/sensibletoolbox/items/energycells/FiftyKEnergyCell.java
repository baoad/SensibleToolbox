package io.github.thebusybiscuit.sensibletoolbox.items.energycells;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.items.components.EnergizedIronIngot;

public class FiftyKEnergyCell extends EnergyCell {

    public FiftyKEnergyCell() {
        super();
    }

    public FiftyKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 50000;
    }

    @Override
    public int getChargeRate() {
        return 500;
    }

    @Override
    public Color getCellColor() {
        return Color.PURPLE;
    }

    @Override
    public String getItemName() {
        return "§e50K 电池组";
    }
    
    @Override
    public String[] getLore() {
        return new String[] { "用于储存 §6SCU能量 §7给物品/设备使用", "拿在手中 §6右键 §7可以给 §6快捷栏", "内的物品充电", "能源储量: §650,000 §7SCU" };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        TenKEnergyCell cell = new TenKEnergyCell();
        cell.setCharge(0.0);
        EnergizedIronIngot ei = new EnergizedIronIngot();
        registerCustomIngredients(cell, ei);
        recipe.shape("III", "CCC", "GRG");
        recipe.setIngredient('I', ei.getMaterial());
        recipe.setIngredient('C', cell.getMaterial());
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;
    }
}
