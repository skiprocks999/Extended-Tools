package extendedtools.common.tab.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ExtendedItemTier implements IItemTier {
    STEEL(2, (int) (ItemTier.IRON.getMaxUses() * 2.7), ItemTier.IRON.getEfficiency(),
	    ItemTier.IRON.getAttackDamage() * 1.5f, 5),
    BRONZE(2, ItemTier.IRON.getMaxUses(), ItemTier.IRON.getEfficiency(), ItemTier.IRON.getAttackDamage(), 5),
    TIN(1, ItemTier.STONE.getMaxUses(), ItemTier.STONE.getEfficiency(), ItemTier.IRON.getAttackDamage(), 5),
    COPPER(1, ItemTier.STONE.getMaxUses() * 2, ItemTier.STONE.getEfficiency(), ItemTier.STONE.getAttackDamage(), 5),
    LEAD(2, ItemTier.DIAMOND.getMaxUses() * 2, ItemTier.STONE.getEfficiency(), ItemTier.IRON.getAttackDamage(), 5),
    VANADIUM(2, ItemTier.STONE.getMaxUses(), ItemTier.IRON.getEfficiency(), ItemTier.IRON.getAttackDamage(), 5),
    SILVER(2, 600, ItemTier.GOLD.getEfficiency(), ItemTier.GOLD.getAttackDamage(), 5);

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
    public int getMaxUses() {
	return maxUses;
    }

    @Override
    public float getEfficiency() {
	return efficency;
    }

    @Override
    public float getAttackDamage() {
	return attackDammage;
    }

    @Override
    public int getHarvestLevel() {
	return harvestLevel;
    }

    @Override
    public int getEnchantability() {
	return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
	return Ingredient.EMPTY;
    }

    public String tag() {
	return name().toLowerCase();
    }

}
