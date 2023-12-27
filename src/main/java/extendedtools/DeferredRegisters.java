package extendedtools;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Supplier;

import extendedtools.common.item.ArmorMaterialList;
import extendedtools.common.item.ExtendedItemTier;
import extendedtools.common.item.PaxelItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class DeferredRegisters {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static PaxelItem icon;
	static {
		List<String> types = Arrays.<String>asList("axe", "hoe", "pickaxe", "shovel", "sword", "paxel");
		for (String type : types) {
			for (ExtendedItemTier tier : ExtendedItemTier.values()) {
				Item reg = null;
				switch (type) {
				case "axe":
					reg = new AxeItem(tier, tier.getAttackDamageBonus(), -3.0f, new Properties().tab(References.CORETAB));
					break;
				case "hoe":
					reg = new HoeItem(tier, (int) tier.getAttackDamageBonus(), 0f, new Properties().tab(References.CORETAB));
					break;
				case "pickaxe":
					reg = new PickaxeItem(tier, (int) tier.getAttackDamageBonus(), -2.8f, new Properties().tab(References.CORETAB));
					break;
				case "shovel":
					reg = new ShovelItem(tier, tier.getAttackDamageBonus(), -3.0f, new Properties().tab(References.CORETAB));
					break;
				case "sword":
					reg = new SwordItem(tier, (int) tier.getAttackDamageBonus(), -2.4f, new Properties().tab(References.CORETAB));
					break;
				case "paxel":
					reg = new PaxelItem(tier, new Properties().tab(References.CORETAB));
					break;
				default:
					break;
				}
				ITEMS.register(type + tier.tag(), supplier(reg));
			}
		}
		for (ItemTier tier : ItemTier.values()) {
			PaxelItem item = new PaxelItem(tier, new Properties().tab(References.CORETAB));
			ITEMS.register("paxel" + tier.name().toLowerCase(), supplier(item));
			if (tier == ItemTier.NETHERITE) {
				icon = item;
			}
		}
		for (ArmorMaterialList armor : ArmorMaterialList.values()) {
			for (EquipmentSlotType type : EquipmentSlotType.values()) {
				if (type != EquipmentSlotType.MAINHAND && type != EquipmentSlotType.OFFHAND) {
					ITEMS.register(type.getName() + armor.getName().replace(References.ID + ":", ""), supplier(new ArmorItem(armor, type, new Properties().tab(References.CORETAB))));
				}
			}
		}
	}

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}
}
