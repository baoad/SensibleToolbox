package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import me.desht.dhutils.Debugger;

public class ReceiverModule extends ItemRouterModule {

    public ReceiverModule() {}

    public ReceiverModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b接收器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放在 §6量子物品路由器 §7内使用", "用来从面对的传送器模块内 §6接收 §7物品", "或者从 §6链接 §7的高级传送器内 §6接收 §7物品" };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.HOPPER);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.ORANGE_DYE;
    }

    public int receiveItem(ItemStack item, UUID senderUUID) {
        int received = getItemRouter().insertItems(item, BlockFace.SELF, false, senderUUID);
        if (received > 0) {
            Debugger.getInstance().debug(2, "receiver in " + getItemRouter() + " received " + received + " of " + item + ", now has " + getItemRouter().getBufferItem());
        }
        return received;
    }
}
