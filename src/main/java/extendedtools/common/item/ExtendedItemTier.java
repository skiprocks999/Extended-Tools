package extendedtools.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public enum ExtendedItemTier implements Tier {

    //
    STEEL(BlockTags.INCORRECT_FOR_IRON_TOOL, (int) (Tiers.IRON.getUses() * 2.7), Tiers.IRON.getSpeed(), Tiers.IRON.getAttackDamageBonus() * 1.2f, 5, Tags.INGOT_STEEL),
    //
    BRONZE(BlockTags.INCORRECT_FOR_IRON_TOOL, Tiers.IRON.getUses(), Tiers.IRON.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5, Tags.INGOT_BRONZE),
    //
    TIN(BlockTags.INCORRECT_FOR_STONE_TOOL, Tiers.STONE.getUses(), Tiers.STONE.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5, Tags.INGOT_TIN),
    //
    COPPER(BlockTags.INCORRECT_FOR_STONE_TOOL, Tiers.STONE.getUses() * 2, Tiers.STONE.getSpeed(), Tiers.STONE.getAttackDamageBonus(), 5, net.neoforged.neoforge.common.Tags.Items.INGOTS_COPPER),
    //
    LEAD(BlockTags.INCORRECT_FOR_IRON_TOOL, Tiers.DIAMOND.getUses() * 2, Tiers.STONE.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5, Tags.INGOT_LEAD),
    //
    VANADIUM(BlockTags.INCORRECT_FOR_IRON_TOOL, Tiers.STONE.getUses(), Tiers.IRON.getSpeed(), Tiers.IRON.getAttackDamageBonus(), 5, Tags.INGOT_VANADIUM),
    //
    SILVER(BlockTags.INCORRECT_FOR_IRON_TOOL, (int) (Tiers.IRON.getUses() * 1.3), Tiers.GOLD.getSpeed(), Tiers.GOLD.getAttackDamageBonus(), 5, Tags.INGOT_SILVER),
    //
    TITANIUM(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, Tiers.IRON.getUses() * 4, Tiers.IRON.getSpeed() * 1.1f, Tiers.IRON.getAttackDamageBonus() * 1.3f, 5, Tags.INGOT_TITANIUM);

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int maxUses;
    private final float efficency;
    private final float attackDammage;
    private final int enchantability;
    private final Ingredient repairIngredient;

    ExtendedItemTier(TagKey<Block> incorrectBlocksForDrops, int maxUses, float efficency, float attackDammage, int enchantability, TagKey<Item> repairTag) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.maxUses = maxUses;
        this.efficency = efficency;
        this.attackDammage = attackDammage;
        this.enchantability = enchantability;
        repairIngredient = Ingredient.of(repairTag);
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
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocksForDrops;
    }

    @Override
    public Tool createToolProperties(TagKey<Block> pBlock) {
        return new Tool(List.of(Tool.Rule.deniesDrops(this.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(pBlock, this.getSpeed())), 1.0F, 0);
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    public String tag() {
        return name().toLowerCase();
    }

}
