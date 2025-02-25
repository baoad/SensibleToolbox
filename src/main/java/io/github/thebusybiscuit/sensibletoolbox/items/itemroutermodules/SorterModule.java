package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.STBInventoryHolder;
import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.utils.VanillaInventoryUtils;
import me.desht.dhutils.Debugger;

public class SorterModule extends DirectionalItemRouterModule {

    public SorterModule() {}

    public SorterModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.PURPLE_DYE;
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore("放在 §6量子物品路由器 §7内使用", "可以自动传输 §6符合条件 §7的物品", "工作条件: 对应的容器是 §6空 §7的或者 §6包含 §7要传输的物品", "", "左键方块: §6设置方向", "蹲下 + 左键空气: 删除方向");
    }

    @Override
    public Recipe getRecipe() {
        registerCustomIngredients(new BlankModule());
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(Material.PAPER);
        recipe.addIngredient(Material.SPIDER_EYE);
        recipe.addIngredient(Material.ARROW);
        return recipe;
    }

    @Override
    public boolean execute(Location loc) {
        if (getItemRouter() != null && getItemRouter().getBufferItem() != null) {
            if (getFilter() != null && !getFilter().shouldPass(getItemRouter().getBufferItem())) {
                return false;
            }

            Debugger.getInstance().debug(2, "sorter in " + getItemRouter() + " has: " + getItemRouter().getBufferItem());
            Location targetLoc = getTargetLocation(loc);
            int nToInsert = getItemRouter().getStackSize();
            BaseSTBBlock stb = SensibleToolbox.getBlockAt(targetLoc, true);
            int nInserted;

            if (stb instanceof STBInventoryHolder) {
                ItemStack toInsert = getItemRouter().getBufferItem().clone();
                toInsert.setAmount(Math.min(nToInsert, toInsert.getAmount()));
                nInserted = ((STBInventoryHolder) stb).insertItems(toInsert, getFacing().getOppositeFace(), true, getItemRouter().getOwner());
            } else {
                // vanilla inventory holder?
                nInserted = vanillaSortInsertion(targetLoc.getBlock(), nToInsert, getFacing().getOppositeFace());
            }

            getItemRouter().reduceBuffer(nInserted);
            return nInserted > 0;
        }
        return false;
    }

    private int vanillaSortInsertion(Block target, int amount, BlockFace side) {
        ItemStack buffer = getItemRouter().getBufferItem();
        int nInserted = VanillaInventoryUtils.vanillaInsertion(target, buffer, amount, side, true, getItemRouter().getOwner());

        if (nInserted > 0) {
            getItemRouter().setBufferItem(buffer.getAmount() == 0 ? null : buffer);
        }

        return nInserted;
    }
}
