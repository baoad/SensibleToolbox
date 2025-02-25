package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import java.util.List;

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

public class DistributorModule extends DirectionalItemRouterModule {

    private int nextNeighbour = 0;

    public DistributorModule() {}

    public DistributorModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.RED_DYE;
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b分发器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "需要放在 §6量子物品路由器 §7内使用", "可以自动从配置好的方向的容器内 §6提取 §7物品", "然后按顺序 §6传送 §7到相邻的容器内", "", "左键方块: §6设置方向" };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.STICKY_PISTON);
        recipe.addIngredient(Material.ARROW);
        return recipe;
    }

    @Override
    public boolean execute(Location loc) {
        if (getItemRouter() == null) {
            // shouldn't happen...
            return false;
        }

        doPull(getFacing(), loc);

        if (getItemRouter().getNeighbours().size() > 1 && getItemRouter().getBufferItem() != null) {
            int nToInsert = getItemRouter().getStackSize();
            BlockFace face = getNextNeighbour();

            if (face == getFacing()) {
                // shouldn't happen...
                return false;
            }

            Block b = loc.getBlock();
            Block target = b.getRelative(face);
            BaseSTBBlock stb = SensibleToolbox.getBlockAt(target.getLocation(), true);

            if (stb instanceof STBInventoryHolder) {
                ItemStack toInsert = getItemRouter().getBufferItem().clone();
                toInsert.setAmount(Math.min(nToInsert, toInsert.getAmount()));
                int nInserted = ((STBInventoryHolder) stb).insertItems(toInsert, face.getOppositeFace(), false, getItemRouter().getOwner());
                getItemRouter().reduceBuffer(nInserted);
                return nInserted > 0;
            } else {
                // vanilla inventory holder?
                return vanillaInsertion(target, nToInsert, getFacing().getOppositeFace());
            }
        }

        return false;
    }

    private BlockFace getNextNeighbour() {
        List<BlockFace> neighbours = getItemRouter().getNeighbours();
        nextNeighbour = (nextNeighbour + 1) % neighbours.size();

        if (neighbours.get(nextNeighbour) == getFacing()) {
            nextNeighbour = (nextNeighbour + 1) % neighbours.size();
        }

        return neighbours.get(nextNeighbour);
    }
}
