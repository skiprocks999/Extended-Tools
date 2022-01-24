package extendedtools.common.item;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;

public class PaxelItem extends DiggerItem {

	public PaxelItem(Tier tier, Properties prop) {
		super(4, -2.4f, tier, BlockTags.MINEABLE_WITH_AXE, prop.durability(tier.getUses() * 2));
	}

	@Deprecated(forRemoval = true)
	// FORGE: Use stack sensitive variant below
	// ONLY ADDED CAUSE IT EXISTS IN THE DiggerItem.class!!!
	@Override
	public boolean isCorrectToolForDrops(BlockState state) {
		if (net.minecraftforge.common.TierSortingRegistry.isTierSorted(getTier())) {
			return net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state) && (state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE) || state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL));
		}
		int i = getTier().getLevel();
		if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL) || i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
			return false;
		}
		return i >= 1 && state.is(BlockTags.NEEDS_STONE_TOOL) && (state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE) || state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL));
	}

	// FORGE START
	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return (state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE) || state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state);
	}

	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
		if (isCorrectToolForDrops(stack, state)) {
			return getTier().getSpeed();
		}
		return 1;
	}

	@Nonnull
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockState state = world.getBlockState(pos);
		BlockState result = state.getToolModifiedState(world, pos, player, stack, ToolActions.AXE_STRIP);
		if (result != null) {
			world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
		} else {
			if (context.getClickedFace() == Direction.DOWN) {
				return InteractionResult.PASS;
			}
			BlockState foundResult = state.getToolModifiedState(world, pos, player, stack, ToolActions.SHOVEL_FLATTEN);
			if (foundResult != null && world.isEmptyBlock(pos.above())) {
				world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
				result = foundResult;
			} else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT) == Boolean.TRUE) {
				if (!world.isClientSide) {
					world.levelEvent(null, 1009 /* WorldEvents.FIRE_EXTINGUISH_SOUND */, pos, 0);
				}
				CampfireBlock.dowse(player, world, pos, state);
				result = state.setValue(CampfireBlock.LIT, false);
			}
		}
		if (result == null) {
			return InteractionResult.PASS;
		}
		if (!world.isClientSide) {
			world.setBlock(pos, result, Block.UPDATE_ALL);
			if (player != null) {
				stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(context.getHand()));
			}
		}
		return InteractionResult.sidedSuccess(world.isClientSide);
	}
}
