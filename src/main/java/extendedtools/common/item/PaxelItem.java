package extendedtools.common.item;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants.BlockFlags;
import net.minecraftforge.common.util.Constants.WorldEvents;

public class PaxelItem extends ToolItem {
    private static final Set<Material> AXE_EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD,
	    Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);

    public PaxelItem(IItemTier tier, Properties prop) {
	super(4, -2.4f, tier, Collections.emptySet(),
		prop.addToolType(ToolType.AXE, tier.getHarvestLevel()).addToolType(ToolType.HOE, tier.getHarvestLevel())
			.addToolType(ToolType.PICKAXE, tier.getHarvestLevel())
			.addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
	ToolType harvestTool = state.getHarvestTool();
	if (harvestTool == ToolType.AXE || harvestTool == ToolType.PICKAXE || harvestTool == ToolType.SHOVEL) {
	    if (getTier().getHarvestLevel() >= state.getHarvestLevel()) {
		return true;
	    }
	}
	if (state.isIn(Blocks.SNOW) || state.isIn(Blocks.SNOW_BLOCK)) {
	    return true;
	}
	Material material = state.getMaterial();
	return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
	Material material = state.getMaterial();
	if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK
		|| AXE_EFFECTIVE_ON_MATERIALS.contains(material)
		|| getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
	    return getTier().getEfficiency();
	}
	return 1;
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
	World world = context.getWorld();
	BlockPos pos = context.getPos();
	PlayerEntity player = context.getPlayer();
	ItemStack stack = context.getItem();
	BlockState state = world.getBlockState(pos);
	BlockState result = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);
	if (result != null) {
	    world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
	} else {
	    if (context.getFace() == Direction.DOWN) {
		return ActionResultType.PASS;
	    }
	    BlockState foundResult = state.getToolModifiedState(world, pos, player, stack, ToolType.SHOVEL);
	    if (foundResult != null && world.isAirBlock(pos.up())) {
		world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
		result = foundResult;
	    } else if (state.getBlock() instanceof CampfireBlock && state.get(CampfireBlock.LIT)) {
		if (!world.isRemote) {
		    world.playEvent(null, WorldEvents.FIRE_EXTINGUISH_SOUND, pos, 0);
		}
		CampfireBlock.extinguish(world, pos, state);
		result = state.with(CampfireBlock.LIT, false);
	    }
	}
	if (result == null) {
	    return ActionResultType.PASS;
	}
	if (!world.isRemote) {
	    world.setBlockState(pos, result, BlockFlags.DEFAULT_AND_RERENDER);
	    if (player != null) {
		stack.damageItem(1, player, onBroken -> onBroken.sendBreakAnimation(context.getHand()));
	    }
	}
	return ActionResultType.func_233537_a_(world.isRemote);
    }
}
