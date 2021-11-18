package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class IronDust extends BaseSTBItem {

    public IronDust() {
        super();
    }

    public IronDust(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    public String getItemName() {
        return "§7铁矿粉末";
    }

    @Override
    public String[] getLore() {
        return new String[] { "在 §6量子烧制器 §7中可以制作成铁锭" };
    }

    @Override
    public Recipe getRecipe() {
        // Only made by the Masher
        return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new ItemStack(Material.IRON_INGOT);
    }
}
