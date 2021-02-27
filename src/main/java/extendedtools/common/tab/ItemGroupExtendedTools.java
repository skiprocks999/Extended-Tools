package extendedtools.common.tab;

import extendedtools.DeferredRegisters;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

public class ItemGroupExtendedTools extends ItemGroup {

    public ItemGroupExtendedTools(String label) {
	super(label);
    }

    @Override
    public ItemStack createIcon() {
	return new ItemStack(((RegistryObject<Item>) DeferredRegisters.ITEMS.getEntries()
		.toArray()[DeferredRegisters.ITEMS.getEntries().size() - 1]).get());
    }
}