package extendedtools.common.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemGroupExtendedTools extends ItemGroup {

    public ItemGroupExtendedTools(String label) {
	super(label);
    }

    @Override
    public ItemStack createIcon() {
	return new ItemStack(Items.DIAMOND_AXE);
    }
}