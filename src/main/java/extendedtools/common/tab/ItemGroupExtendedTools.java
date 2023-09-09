package extendedtools.common.tab;

import extendedtools.DeferredRegisters;
import extendedtools.References;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupExtendedTools extends CreativeModeTab {

	public ItemGroupExtendedTools(String label) {
		super(CreativeModeTab.builder().displayItems(new DisplayItemsGenerator() {

			@Override
			public void accept(ItemDisplayParameters pParameters, Output pOutput) {

			}
		}).title(Component.translatable("creativetab." + References.ID + "." + label)).icon(() -> new ItemStack(DeferredRegisters.icon.get())));
	}
}