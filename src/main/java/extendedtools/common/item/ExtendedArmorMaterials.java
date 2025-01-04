package extendedtools.common.item;

import extendedtools.References;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.EnumMap;
import java.util.Locale;

public enum ExtendedArmorMaterials {

    STEEL((int) (15 * 2.7), new int[]{3, 5, 7, 3}, 10, 1, 0, Tags.INGOT_STEEL, "steel"),
    BRONZE(15, new int[]{2, 5, 6, 2}, 10, 0, 0, Tags.INGOT_BRONZE, "bronze"),
    TIN(12, new int[]{2, 4, 5, 2}, 10, 0, 0, Tags.INGOT_TIN, "tin"),
    COPPER(12, new int[]{2, 4, 5, 2}, 10, 0, 0, net.neoforged.neoforge.common.Tags.Items.INGOTS_COPPER, "copper"),
    LEAD(30, new int[]{2, 4, 5, 2}, 10, 0, 3, Tags.INGOT_LEAD, "lead"),
    VANADIUM(15, new int[]{3, 4, 5, 3}, 10, 0, 0, Tags.INGOT_VANADIUM, "vanadium"),
    SILVER(20, new int[]{3, 5, 7, 3}, 10, 0, 0, Tags.INGOT_SILVER, "silver"),
    TITANIUM(60, new int[]{4, 6, 8, 4}, 10, 2, 0, Tags.INGOT_TITANIUM, "titanium");

    public final EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
    public final int maxDamage;
    public final int enchantability;
    public final float toughness;
    public final float knockbackRes;
    public final TagKey<Item> repairIng;
    public final ArmorMaterial.Layer layer;

    private ExtendedArmorMaterials(int maxDamageFactor, int[] dmgArray, int enchantability, float toughness, float knockbackResistance, TagKey<Item> repairIng, String name) {
        maxDamage = maxDamageFactor;
        map.put(ArmorItem.Type.HELMET, dmgArray[0]);
        map.put(ArmorItem.Type.CHESTPLATE, dmgArray[1]);
        map.put(ArmorItem.Type.LEGGINGS, dmgArray[2]);
        map.put(ArmorItem.Type.BOOTS, dmgArray[3]);
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.knockbackRes = knockbackResistance;
        this.repairIng = repairIng;
        layer = new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(References.ID, name));
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
