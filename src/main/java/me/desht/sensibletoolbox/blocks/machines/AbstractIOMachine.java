package me.desht.sensibletoolbox.blocks.machines;

import me.desht.sensibletoolbox.SensibleToolboxPlugin;
import me.desht.sensibletoolbox.recipes.CustomRecipeManager;
import me.desht.sensibletoolbox.recipes.ProcessingResult;
import me.desht.sensibletoolbox.util.STBUtil;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a machine which processes items from its input slots to
 * an internal processing store, and places resulting items in its output slots.
 */
public abstract class AbstractIOMachine extends AbstractProcessingMachine {

    protected AbstractIOMachine() {
        super();
    }

    public AbstractIOMachine(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getTickRate() {
        return 10;
    }

    @Override
    protected void playOutOfChargeSound() {
        getLocation().getWorld().playSound(getLocation(), Sound.ENDERDRAGON_HIT, 1.0f, 0.5f);
    }

    @Override
    public void onServerTick() {
        if (!isRedstoneActive()) {
            return;
        }

        if (getProcessing() == null) {
            // not doing any processing - anything in input to take?
            for (int slot : getInputSlots()) {
                if (getInventoryItem(slot) != null) {
                    pullItemIntoProcessing(slot);
                    if (SensibleToolboxPlugin.getInstance().getConfig().getBoolean("noisy_machines")) {
                        onMachineStartup();
                    }
                    break;
                }
            }
        }

        if (getProgress() > 0 && getCharge() > 0) {
            // currently processing....
            setProgress(getProgress() - getSpeedMultiplier() * getTickRate());
            setCharge(getCharge() - getPowerMultiplier() * getTickRate());
            playActiveParticleEffect();
        }

        if (getProcessing() != null && getProgress() <= 0 && !isJammed()) {
            // done processing - try to move item into output
            ProcessingResult recipe = getCustomRecipeFor(getProcessing());
            if (recipe != null) {
                // shouldn't ever be null, but let's be paranoid here
                pushItemIntoOutput(recipe.getResult());
            }
        }

        handleAutoEjection();

        super.onServerTick();
    }

    private void pushItemIntoOutput(ItemStack result) {
        if (result != null && result.getAmount() > 0) {
            int slot = findOutputSlot(result);
            if (slot >= 0) {
                // good, there's space to move it out of processing
                ItemStack stack = getInventoryItem(slot);
                if (stack == null) {
                    stack = result;
                } else {
                    stack.setAmount(stack.getAmount() + result.getAmount());
                }
                setInventoryItem(slot, stack);
            } else {
                // no space!
                setJammed(true);
            }
        }

        if (!isJammed()) {
            setProcessing(null);
            updateBlock(false);
        }
    }

    private void pullItemIntoProcessing(int inputSlot) {
        ItemStack stack = getInventoryItem(inputSlot);
        ItemStack toProcess = stack.clone();
        toProcess.setAmount(1);
        ProcessingResult recipe = getCustomRecipeFor(toProcess);
        if (recipe == null) {
            // shouldn't happen but...
            getLocation().getWorld().dropItemNaturally(getLocation(), stack);
            setInventoryItem(inputSlot, null);
            return;
        }
        setProcessing(toProcess);
        getProgressMeter().setMaxProgress(recipe.getProcessingTime());
        setProgress(recipe.getProcessingTime());
        stack.setAmount(stack.getAmount() - 1);
        setInventoryItem(inputSlot, stack);

        if (stack == null) {
            // workaround to avoid leaving ghost items in the input slot
            STBUtil.forceInventoryRefresh(getInventory());
        }

        updateBlock(false);
    }


    @Override
    public boolean acceptsItemType(ItemStack item) {
        return CustomRecipeManager.getManager().hasRecipe(this, item);
    }

    protected ProcessingResult getCustomRecipeFor(ItemStack stack) {
        return CustomRecipeManager.getManager().getRecipe(this, stack);
    }

    @Override
    public boolean acceptsEnergy(BlockFace face) {
        return true;
    }

    @Override
    public boolean suppliesEnergy(BlockFace face) {
        return false;
    }
}
