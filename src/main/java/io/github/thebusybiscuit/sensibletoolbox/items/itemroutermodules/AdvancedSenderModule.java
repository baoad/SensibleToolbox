package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.blocks.router.ItemRouter;
import io.github.thebusybiscuit.sensibletoolbox.utils.STBUtil;
import io.github.thebusybiscuit.sensibletoolbox.utils.UnicodeSymbol;
import me.desht.dhutils.Debugger;
import me.desht.dhutils.MiscUtil;

public class AdvancedSenderModule extends DirectionalItemRouterModule {

    private static final int RANGE = 24;
    private static final int RANGE2 = RANGE * RANGE;
    private Location linkedLoc;

    public AdvancedSenderModule() {
        linkedLoc = null;
    }

    public AdvancedSenderModule(ConfigurationSection conf) {
        super(conf);

        if (conf.contains("linkedLoc")) {
            try {
                linkedLoc = MiscUtil.parseLocation(conf.getString("linkedLoc"));
            } catch (IllegalArgumentException e) {
                linkedLoc = null;
            }
        }
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        if (linkedLoc != null) {
            conf.set("linkedLoc", MiscUtil.formatLocation(linkedLoc));
        }
        return conf;
    }

    @Override
    public Material getMaterial() {
        return Material.LIGHT_BLUE_DYE;
    }

    @Override
    public String getItemName() {
        return "§d路由器模组: §b高级传送器: ";
    }

    @Override
    public String[] getLore() {
        return new String[] { "§7请 §6配置后 §7放在 §6量子物品路由器 §7内使用", "用于 §6传送 §7所在路由器内 §6物品缓冲区", "的物品" ,"到链接的量子物品路由器当中", "工作范围: "+ ChatColor.GOLD + RANGE + "格", "", "左键: 在安装了 §6接收模块的量子物品路由器", "当中", "蹲下 + 左键: 量子物品路由器来 §6取消 §7链接" };
    }

    @Override
    public String getDisplaySuffix() {
        return linkedLoc == null ? "§b[尚未连接]" : "§b[" + MiscUtil.formatLocation(linkedLoc) + "]";
    }

    @Override
    public Recipe getRecipe() {
        SenderModule sm = new SenderModule();
        registerCustomIngredients(sm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(sm.getMaterial());
        recipe.addIngredient(Material.ENDER_EYE);
        recipe.addIngredient(Material.DIAMOND);
        return recipe;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
            // try to link up with a receiver module
            ItemRouter rtr = SensibleToolbox.getBlockAt(event.getClickedBlock().getLocation(), ItemRouter.class, true);
            if (rtr != null && rtr.getReceiver() != null) {
                linkToRouter(rtr);
                updateHeldItemStack(event.getPlayer(), event.getHand());
            } else {
                STBUtil.complain(event.getPlayer());
            }
            event.setCancelled(true);
        } else if (event.getPlayer().isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            linkToRouter(null);
            updateHeldItemStack(event.getPlayer(), event.getHand());
            event.setCancelled(true);
        } else if (event.getItem().getAmount() == 1 && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            super.onInteractItem(event);
        }
    }

    public void linkToRouter(ItemRouter rtr) {
        linkedLoc = rtr == null ? null : rtr.getLocation();
    }

    protected boolean inRange(Location ourLoc) {
        return ourLoc != null && ourLoc.getWorld().equals(linkedLoc.getWorld()) && ourLoc.distanceSquared(linkedLoc) <= RANGE2;
    }

    @Override
    public boolean execute(Location loc) {
        if (getItemRouter() != null && getItemRouter().getBufferItem() != null && linkedLoc != null) {
            if (getFilter() != null && !getFilter().shouldPass(getItemRouter().getBufferItem())) {
                return false;
            }

            ItemRouter otherRouter = SensibleToolbox.getBlockAt(linkedLoc, ItemRouter.class, false);

            if (otherRouter != null) {
                if (!inRange(loc)) {
                    return false;
                }

                ReceiverModule mod = otherRouter.getReceiver();
                if (mod != null) {
                    return sendItems(mod) > 0;
                }
            }
        }
        return false;
    }

    private int sendItems(ReceiverModule receiver) {
        Debugger.getInstance().debug(this.getItemRouter() + ": adv.sender sending items to receiver module in " + receiver.getItemRouter());
        int nToSend = getItemRouter().getStackSize();
        ItemStack toSend = getItemRouter().getBufferItem().clone();
        toSend.setAmount(Math.min(nToSend, toSend.getAmount()));
        int received = receiver.receiveItem(toSend, getItemRouter().getOwner());
        getItemRouter().reduceBuffer(received);
        return received;
    }
}
