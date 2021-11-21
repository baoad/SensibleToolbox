package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.Vector;

import me.desht.dhutils.Debugger;

public class DropperModule extends DirectionalItemRouterModule {

    public DropperModule() {}

    public DropperModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b投掷器";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore("放在 §6量子物品路由器 §7内使用", "可以把路由器 §6缓冲区内 §7的物品", "向配置好的方向 §6投掷物品 §7在地上", "" );
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.DROPPER);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.GRAY_DYE;
    }

    @Override
    public boolean execute(Location loc) {
        if (getItemRouter() != null && getItemRouter().getBufferItem() != null) {
            if (getFilter() != null && !getFilter().shouldPass(getItemRouter().getBufferItem())) {
                return false;
            }

            int toDrop = getItemRouter().getStackSize();
            ItemStack stack = getItemRouter().extractItems(BlockFace.SELF, null, toDrop, null);

            if (stack != null) {
                Location targetLoc = getTargetLocation(loc).add(0.5, 0.5, 0.5);
                Item item = targetLoc.getWorld().dropItem(targetLoc, stack);
                item.setVelocity(new Vector(0, 0, 0));
                Debugger.getInstance().debug(2, "dropper dropped " + stack + " from " + getItemRouter());
            }

            return true;
        }
        return false;
    }
}
