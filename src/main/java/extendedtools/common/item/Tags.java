package extendedtools.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {

    public static final TagKey<Item> INGOT_BRONZE = forgeTag("ingots/bronze");
    public static final TagKey<Item> INGOT_LEAD = forgeTag("ingots/lead");
    public static final TagKey<Item> INGOT_SILVER = forgeTag("ingots/silver");
    public static final TagKey<Item> INGOT_STEEL = forgeTag("ingots/steel");
    public static final TagKey<Item> INGOT_TIN = forgeTag("ingots/tin");
    public static final TagKey<Item> INGOT_TITANIUM = forgeTag("ingots/titanium");
    public static final TagKey<Item> INGOT_VANADIUM = forgeTag("ingots/vanadium");

    public static final TagKey<Block> PAXEL_BLOCKS = BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "paxelblocks"));

    public static void init() {

    }

    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }

}
