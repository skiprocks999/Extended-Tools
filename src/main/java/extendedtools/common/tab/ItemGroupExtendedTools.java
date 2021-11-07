package extendedtools.common.tab;

import extendedtools.DeferredRegisters;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupExtendedTools extends CreativeModeTab {

    public ItemGroupExtendedTools(String label) {
	super(label);
    }

    @Override
    public ItemStack makeIcon() {
	return new ItemStack(DeferredRegisters.icon);
    }
}