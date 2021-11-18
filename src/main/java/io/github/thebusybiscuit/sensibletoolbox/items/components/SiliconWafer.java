package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class SiliconWafer extends BaseSTBItem {

    public SiliconWafer() {}

    public SiliconWafer(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.FIREWORK_STAR;
    }

    @Override
    public String getItemName() {
        return "§b硅晶片";
    }

    @Override
    public String[] getLore() {
        return new String[] { "是制造 §6高级集成电路 §7的原材料" };
    }

    @Override
    public Recipe getRecipe() {
        // made in a smelter
        return null;
    }
}
