package extendedtools.common.item;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
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
	private static final Set<Material> AXE_EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);

	public PaxelItem(IItemTier tier, Properties prop) {
		super(4, -2.4f, tier, Collections.emptySet(), prop.addToolType(ToolType.AXE, tier.getLevel()).addToolType(ToolType.HOE, tier.getLevel()).addToolType(ToolType.PICKAXE, tier.getLevel()).addToolType(ToolType.SHOVEL, tier.getLevel()).durability(tier.getUses() * 2));
	}

	@Override
	public boolean mineBlock(ItemStack stack, World level, BlockState state, BlockPos pos, LivingEntity entity) {
		ToolType harvestTool = state.getHarvestTool();
		if ((harvestTool == ToolType.AXE || harvestTool == ToolType.PICKAXE || harvestTool == ToolType.SHOVEL) && getTier().getLevel() >= state.getHarvestLevel()) {
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
		if (material == Material.METAL || material == Material.HEAVY_METAL || material == Material.STONE || AXE_EFFECTIVE_ON_MATERIALS.contains(material) || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
			return getTier().getSpeed();
		}
		return 1;
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockState state = world.getBlockState(pos);
		BlockState result = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);
		if (result != null) {
			world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
		} else {
			if (context.getClickedFace() == Direction.DOWN) {
				return ActionResultType.PASS;
			}
			BlockState foundResult = state.getToolModifiedState(world, pos, player, stack, ToolType.SHOVEL);
			if (foundResult != null && state.isAir(world, pos)) {
				world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				result = foundResult;
			} else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT) == Boolean.TRUE) {
				if (!world.isClientSide) {
					world.levelEvent(null, WorldEvents.FIRE_EXTINGUISH_SOUND, pos, 0);
				}
				CampfireBlock.dowse(world, pos, state);
				result = state.setValue(CampfireBlock.LIT, false);
			}
		}
		if (result == null) {
			return ActionResultType.PASS;
		}
		if (!world.isClientSide) {
			world.setBlock(pos, result, BlockFlags.DEFAULT_AND_RERENDER);
			if (player != null) {
				stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(context.getHand()));
			}
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}

}
