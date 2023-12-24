package extendedtools.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ExtendedItemTier implements IItemTier {
	STEEL(2, (int) (ItemTier.IRON.getUses() * 2.7), ItemTier.IRON.getSpeed(), ItemTier.IRON.getAttackDamageBonus() * 1.5f, 5),
	//
	BRONZE(2, ItemTier.IRON.getUses(), ItemTier.IRON.getSpeed(), ItemTier.IRON.getAttackDamageBonus(), 5),
	//
	TIN(1, ItemTier.STONE.getUses(), ItemTier.STONE.getSpeed(), ItemTier.IRON.getAttackDamageBonus(), 5),
	//
	COPPER(1, ItemTier.STONE.getUses() * 2, ItemTier.STONE.getSpeed(), ItemTier.STONE.getAttackDamageBonus(), 5),
	//
	LEAD(2, ItemTier.DIAMOND.getUses() * 2, ItemTier.STONE.getSpeed(), ItemTier.IRON.getAttackDamageBonus(), 5),
	//
	VANADIUM(2, ItemTier.STONE.getUses(), ItemTier.IRON.getSpeed(), ItemTier.IRON.getAttackDamageBonus(), 5),
	//
	SILVER(2, (int) (ItemTier.IRON.getUses() * 1.3), ItemTier.GOLD.getSpeed(), ItemTier.GOLD.getAttackDamageBonus(), 5),
	//
	TITANIUM(2, ItemTier.IRON.getUses() * 4, ItemTier.IRON.getSpeed() * 1.1f, ItemTier.IRON.getAttackDamageBonus() * 1.6f, 5);
	//

	private final int harvestLevel;
	private final int maxUses;
	private final float efficency;
	private final float attackDammage;
	private final int enchantability;

	ExtendedItemTier(int harvestLevel, int maxUses, float efficency, float attackDammage, int enchantability) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficency = efficency;
		this.attackDammage = attackDammage;
		this.enchantability = enchantability;
	}

	@Override
	public int getUses() {
		return maxUses;
	}

	@Override
	public float getSpeed() {
		return efficency;
	}

	@Override
	public float getAttackDamageBonus() {
		return attackDammage;
	}

	@Override
	public int getLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.EMPTY;
	}

	public String tag() {
		return name().toLowerCase();
	}

}
