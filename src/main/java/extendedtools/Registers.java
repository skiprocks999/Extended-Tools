package extendedtools;

import java.util.HashMap;
import java.util.List;

import com.google.common.base.Supplier;
import extendedtools.common.item.*;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Registers {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, References.ID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, References.ID);

    public static final List<String> TYPES = List.of("axe", "hoe", "pickaxe", "shovel", "sword", "paxel");
    public static final HashMap<String, String> ARMOR_MAP = Util.make(() -> {

        HashMap<String, String> mappings = new HashMap<>();
        mappings.put("helmet", "head");
        mappings.put("chestplate", "chest");
        mappings.put("leggings", "legs");
        mappings.put("boots", "feet");

        return mappings;

    });

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STEEL = register("steel", ExtendedArmorMaterials.STEEL, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BRONZE = register("bronze", ExtendedArmorMaterials.BRONZE, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TIN = register("tin", ExtendedArmorMaterials.TIN, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COPPER = register("copper", ExtendedArmorMaterials.COPPER, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> LEAD = register("lead", ExtendedArmorMaterials.LEAD, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VANADIUM = register("vanadium", ExtendedArmorMaterials.VANADIUM, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SILVER = register("silver", ExtendedArmorMaterials.SILVER, SoundEvents.ARMOR_EQUIP_IRON);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TITANIUM = register("titanium", ExtendedArmorMaterials.TITANIUM, SoundEvents.ARMOR_EQUIP_IRON);

    public static DeferredHolder<Item, PaxelItem> ICON;
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN;

    static {

        for (String type : TYPES) {
            for (ExtendedItemTier tier : ExtendedItemTier.values()) {
                Supplier<Item> reg = null;
                switch (type) {
                    case "axe":
                        reg = () -> new AxeItem(tier, new Properties());
                        break;
                    case "hoe":
                        reg = () -> new HoeItem(tier, new Properties());
                        break;
                    case "pickaxe":
                        reg = () -> new PickaxeItem(tier, new Properties());
                        break;
                    case "shovel":
                        reg = () -> new ShovelItem(tier, new Properties());
                        break;
                    case "sword":
                        reg = () -> new SwordItem(tier, new Properties());
                        break;
                    case "paxel":
                        reg = () -> new PaxelItem(tier, new Properties());
                        break;
                    default:
                        break;
                }
                ITEMS.register(type + tier.tag(), reg);
            }
        }

        for (Tiers tier : Tiers.values()) {
            DeferredHolder<Item, PaxelItem> obj = Registers.ITEMS.register("paxel" + tier.name().toLowerCase(), () -> new PaxelItem(tier, (tier == Tiers.NETHERITE ? new Properties().stacksTo(1).fireResistant() : new Properties().stacksTo(1))));
            if (tier == Tiers.NETHERITE) {
                ICON = obj;
            }
        }

        HashMap<ExtendedArmorMaterials, DeferredHolder> materialMap = new HashMap<>();
        materialMap.put(ExtendedArmorMaterials.STEEL, STEEL);
        materialMap.put(ExtendedArmorMaterials.BRONZE, BRONZE);
        materialMap.put(ExtendedArmorMaterials.TIN, TIN);
        materialMap.put(ExtendedArmorMaterials.COPPER, COPPER);
        materialMap.put(ExtendedArmorMaterials.LEAD, LEAD);
        materialMap.put(ExtendedArmorMaterials.VANADIUM, VANADIUM);
        materialMap.put(ExtendedArmorMaterials.SILVER, SILVER);
        materialMap.put(ExtendedArmorMaterials.TITANIUM, TITANIUM);

        for (ExtendedArmorMaterials material : ExtendedArmorMaterials.values()) {
            for (ArmorItem.Type type : ArmorItem.Type.values()) {
                if(type == ArmorItem.Type.BODY) {
                    continue;
                }
                ITEMS.register(ARMOR_MAP.get(type.getName()) + material.getName(), () -> new ArmorItem(materialMap.get(material), type, new Properties().stacksTo(1)));
            }
        }

        MAIN = CREATIVE_TABS.register("itemgroupextendedtools", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.itemgroupextendedtools")).icon(() -> new ItemStack(ICON.get())).build());

    }


    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, ExtendedArmorMaterials material, Holder<SoundEvent> sound) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(material.map, material.enchantability, sound, () -> Ingredient.of(material.repairIng), List.of(material.layer), material.toughness, material.knockbackRes));
    }

}
