package extendedtools;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Supplier;

import extendedtools.common.item.ArmorMaterialList;
import extendedtools.common.item.ExtendedItemTier;
import extendedtools.common.item.PaxelItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class DeferredRegisters {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static Supplier<PaxelItem> icon;
	static {
		List<String> types = Arrays.<String>asList("axe", "hoe", "pickaxe", "shovel", "sword", "paxel");
		for (String type : types) {
			for (ExtendedItemTier tier : ExtendedItemTier.values()) {
				Supplier<Item> reg = null;
				switch (type) {
				case "axe":
					reg = () -> new AxeItem(tier, tier.getAttackDamageBonus() * 1.15f, -3.0f, new Properties().tab(References.CORETAB));
					break;
				case "hoe":
					reg = () -> new HoeItem(tier, (int) ((int) tier.getAttackDamageBonus() * 0.27f), -1.4f, new Properties().tab(References.CORETAB));
					break;
				case "pickaxe":
					reg = () -> new PickaxeItem(tier, (int) ((int) tier.getAttackDamageBonus() * 0.33f), -2.8f, new Properties().tab(References.CORETAB));
					break;
				case "shovel":
					reg = () -> new ShovelItem(tier, tier.getAttackDamageBonus() * 0.33f, -3.0f, new Properties().tab(References.CORETAB));
					break;
				case "sword":
					reg = () -> new SwordItem(tier, (int) tier.getAttackDamageBonus(), -2.4f, new Properties().tab(References.CORETAB));
					break;
				case "paxel":
					reg = () -> new PaxelItem(tier, new Properties().tab(References.CORETAB));
					break;
				default:
					break;
				}
				ITEMS.register(type + tier.tag(), reg);
			}
		}
		for (Tiers tier : Tiers.values()) {
			Supplier<PaxelItem> supplier = () -> new PaxelItem(tier, new Properties().tab(References.CORETAB));
			ITEMS.register("paxel" + tier.name().toLowerCase(), supplier);
			if (tier == Tiers.NETHERITE) {
				icon = supplier;
			}
		}
		for (ArmorMaterialList armor : ArmorMaterialList.values()) {
			for (EquipmentSlot type : EquipmentSlot.values()) {
				if (type != EquipmentSlot.MAINHAND && type != EquipmentSlot.OFFHAND) {
					ITEMS.register(type.getName() + armor.getName().replace(References.ID + ":", ""), () -> new ArmorItem(armor, type, new Properties().tab(References.CORETAB)));
				}
			}
		}
	}
}
