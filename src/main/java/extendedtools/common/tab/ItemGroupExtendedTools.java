package extendedtools.common.tab;

import com.google.common.base.Supplier;

import extendedtools.DeferredRegisters;
import extendedtools.References;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemGroupExtendedTools extends CreativeModeTab {

	public ItemGroupExtendedTools(String label) {
		super(CreativeModeTab.builder().displayItems(new DisplayItemsGenerator() {

			@Override
			public void accept(ItemDisplayParameters pParameters, Output pOutput) {
				for (Supplier<Item> s : DeferredRegisters.items) {
					pOutput.accept(new ItemStack(s.get()));
				}
			}
		}).title(Component.translatable("creativetab." + References.ID + "." + label)).icon(() -> new ItemStack(DeferredRegisters.icon.get())));
	}
}