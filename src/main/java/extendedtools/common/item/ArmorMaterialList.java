package extendedtools.common.item;

import extendedtools.References;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum ArmorMaterialList implements ArmorMaterial {
	STEEL((int) (15 * 2.7), new int[] { 3, 5, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 1, 0), BRONZE(15, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 0, 0), TIN(12, new int[] { 2, 4, 5, 2 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 0, 0), COPPER(12, new int[] { 2, 4, 5, 2 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 0, 0), LEAD(30, new int[] { 2, 4, 5, 2 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 0, 3), VANADIUM(15, new int[] { 3, 4, 5, 3 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 0, 0), SILVER(20, new int[] { 3, 5, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 0, 0), TITANIUM(60, new int[] { 4, 6, 8, 4 }, 10, SoundEvents.ARMOR_EQUIP_IRON, 2, 0);

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final float knockbackResistance;

	ArmorMaterialList(int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance) {
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) {
		return damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return soundEvent;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.EMPTY;
	}

	@Override
	public String getName() {
		return References.ID + ":" + name().toLowerCase();
	}

	@Override
	public float getToughness() {
		return toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}
}