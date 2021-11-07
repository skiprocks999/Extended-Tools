package extendedtools.common.item;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants.BlockFlags;
import net.minecraftforge.common.util.Constants.WorldEvents;

public class PaxelItem extends DiggerItem {
    private static final Set<Material> AXE_EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT,
	    Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);

    public PaxelItem(Tier tier, Properties prop) {
	super(4, -2.4f, tier, Collections.emptySet(), prop.addToolType(ToolType.AXE, tier.getLevel()).addToolType(ToolType.HOE, tier.getLevel())
		.addToolType(ToolType.PICKAXE, tier.getLevel()).addToolType(ToolType.SHOVEL, tier.getLevel()).durability(tier.getUses() * 2));
    }

    @Override
    @Deprecated
    public boolean isCorrectToolForDrops(BlockState state) {
	ToolType harvestTool = state.getHarvestTool();
	if ((harvestTool == ToolType.AXE || harvestTool == ToolType.PICKAXE || harvestTool == ToolType.SHOVEL)
		&& getTier().getLevel() >= state.getHarvestLevel()) {
	    return true;
	}
	if (state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK)) {
	    return true;
	}
	Material material = state.getMaterial();
	return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
	Material material = state.getMaterial();
	if (material == Material.METAL || material == Material.HEAVY_METAL || material == Material.STONE
		|| AXE_EFFECTIVE_ON_MATERIALS.contains(material) || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
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
		    world.levelEvent(null, WorldEvents.FIRE_EXTINGUISH_SOUND, pos, 0);
		}
		CampfireBlock.dowse(player, world, pos, state);
		result = state.setValue(CampfireBlock.LIT, false);
	    }
	}
	if (result == null) {
	    return InteractionResult.PASS;
	}
	if (!world.isClientSide) {
	    world.setBlock(pos, result, BlockFlags.DEFAULT_AND_RERENDER);
	    if (player != null) {
		stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(context.getHand()));
	    }
	}
	return InteractionResult.sidedSuccess(world.isClientSide);
    }
}
