package io.github.thebusybiscuit.sensibletoolbox.items.energycells;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;

public class TenKEnergyCell extends EnergyCell {

    public TenKEnergyCell() {
        super();
    }

    public TenKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 10000;
    }

    @Override
    public int getChargeRate() {
        return 100;
    }

    @Override
    public Color getCellColor() {
        return Color.MAROON;
    }

    @Override
    public String getItemName() {
        return "§e10K 能量电池";
    }

     @Override
    public String[] getLore() {
        return new String[] { "用于储存 §6SCU能量 §7给物品/设备使用", "拿在手中 §6右键 §7可以给 §6快捷栏", "内的物品充电", "能源储量: §610,000 §7SCU" };
    }
    
    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        recipe.shape("WWW", "WSW", "GRG");
        recipe.setIngredient('W', new MaterialChoice(Tag.PLANKS.getValues().toArray(new Material[0])));
        recipe.setIngredient('S', Material.SUGAR);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;
    }

}
