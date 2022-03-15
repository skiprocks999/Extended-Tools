package extendedtools.common.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

public enum ExtendedItemTier implements Tier {
	STEEL(2, (int) (Tiers.IRON.getUses() * 2.7), Tiers.IRON.getSpeed(), Tiers.IRON.getAttackDamageBonus() * 1.2f, 5),
	BRONZE(2, Tiers.IRON.getUses(), Tiers.IRON.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5),
	TIN(1, Tiers.STONE.getUses(), Tiers.STONE.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5),
	COPPER(1, Tiers.STONE.getUses() * 2, Tiers.STONE.getSpeed(), Tiers.STONE.getAttackDamageBonus(), 5),
	LEAD(2, Tiers.DIAMOND.getUses() * 2, Tiers.STONE.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5),
	VANADIUM(2, Tiers.STONE.getUses(), Tiers.IRON.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5),
	SILVER(2, (int) (Tiers.IRON.getUses() * 1.3), Tiers.GOLD.getSpeed(), 6 + Tiers.GOLD.getAttackDamageBonus(), 5),
	TITANIUM(2, Tiers.IRON.getUses() * 4, Tiers.IRON.getSpeed() * 1.1f, Tiers.IRON.getAttackDamageBonus() * 1.3f, 5);

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
