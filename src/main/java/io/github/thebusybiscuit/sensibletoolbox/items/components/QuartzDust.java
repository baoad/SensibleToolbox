package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class QuartzDust extends BaseSTBItem {

    public QuartzDust() {}

    public QuartzDust(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.SUGAR;
    }

    @Override
    public String getItemName() {
        return "§7石英粉末";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可以在 §6量子烧制器 §7中加工成硅" };
    }

    @Override
    public Recipe getRecipe() {
        // no vanilla recipe - made in a masher
        return null;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new SiliconWafer().toItemStack();
    }
}
