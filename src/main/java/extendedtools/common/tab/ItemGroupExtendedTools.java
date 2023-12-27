package extendedtools.common.tab;

import extendedtools.DeferredRegisters;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupExtendedTools extends ItemGroup {

	public ItemGroupExtendedTools(String label) {
		super(label);
	}
	
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(DeferredRegisters.icon);
	}
	
}