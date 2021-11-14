package io.github.thebusybiscuit.sensibletoolbox.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Colorable;

import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.utils.ColoredMaterial;

public class Elevator extends BaseSTBBlock implements Colorable {

    private DyeColor color;

    public Elevator() {
        color = DyeColor.WHITE;
    }

    public Elevator(ConfigurationSection conf) {
        super(conf);
        color = DyeColor.valueOf(conf.getString("color"));
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("color", color.toString());
        return conf;
    }

    public DyeColor getColor() {
        return color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
        update(true);
    }

    @Override
    public Material getMaterial() {
        return ColoredMaterial.TERRACOTTA.get(color.ordinal());
    }

    @Override
    public String getItemName() {
        return "§b微型电梯";
    }

    @Override
    public String[] getLore() {
        return new String[] { "在同一 §6垂直方向 §6内 §6上下 §7放置微型电梯", "按下 §6跳跃 §7来上升,则 §6蹲下 §7为下降", };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        recipe.shape("WWW", "WPW", "WWW");
        recipe.setIngredient('W', Material.WHITE_WOOL);
        recipe.setIngredient('P', Material.ENDER_PEARL);
        return recipe;
    }

    @Nullable
    public Elevator findOtherElevator(@Nonnull BlockFace direction) {
        Validate.isTrue(direction == BlockFace.UP || direction == BlockFace.DOWN, "方向必须在同一垂直方向");

        Block b = getLocation().getBlock();
        Elevator res = null;

        while (b.getY() > 0 && b.getY() < b.getWorld().getMaxHeight()) {
            b = b.getRelative(direction);

            if (b.getType().isSolid()) {
                res = SensibleToolbox.getBlockAt(b.getLocation(), Elevator.class, false);
                break;
            }
        }

        return (res != null && res.getColor() == getColor()) ? res : null;
    }
}
