package extendedtools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.google.common.base.Supplier;
import extendedtools.common.item.ArmorMaterialList;
import extendedtools.common.item.ExtendedItemTier;
import extendedtools.common.item.PaxelItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class DeferredRegisters {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.ID);
  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
  public static final HashSet<Supplier<Item>> items = new HashSet<>();
  public static Supplier<PaxelItem> icon;
  static {
    List<String> types = Arrays.<String>asList("axe", "hoe", "pickaxe", "shovel", "sword", "paxel");
    for (String type : types) {
      for (ExtendedItemTier tier : ExtendedItemTier.values()) {
        Supplier<Item> reg = null;
        switch (type) {
          case "axe":
            reg = () -> new AxeItem(tier, tier.getAttackDamageBonus() * 1.15f, -3.0f,
                new Properties());
            break;
          case "hoe":
            reg = () -> new HoeItem(tier, (int) ((int) tier.getAttackDamageBonus() * 0.27f), -1.4f,
                new Properties());
            break;
          case "pickaxe":
            reg = () -> new PickaxeItem(tier, (int) ((int) tier.getAttackDamageBonus() * 0.33f),
                -2.8f, new Properties());
            break;
          case "shovel":
            reg = () -> new ShovelItem(tier, tier.getAttackDamageBonus() * 0.33f, -3.0f,
                new Properties());
            break;
          case "sword":
            reg = () -> new SwordItem(tier, (int) tier.getAttackDamageBonus(), -2.4f,
                new Properties());
            break;
          case "paxel":
            reg = () -> new PaxelItem(tier, new Properties());
            break;
          default:
            break;
        }
        RegistryObject<Item> obj = DeferredRegisters.ITEMS.register(type + tier.tag(), reg);
        DeferredRegisters.items.add(() -> obj.get());
      }
    }
    for (Tiers tier : Tiers.values()) {
      RegistryObject<PaxelItem> obj = DeferredRegisters.ITEMS.register(
          "paxel" + tier.name().toLowerCase(), () -> new PaxelItem(tier, new Properties()));
      DeferredRegisters.items.add(() -> obj.get());
      if (tier == Tiers.NETHERITE) {
        DeferredRegisters.icon = () -> obj.get();
      }
    }
    HashMap<String, String> mappings = new HashMap<>();
    mappings.put("helmet", "head");
    mappings.put("chestplate", "chest");
    mappings.put("leggings", "legs");
    mappings.put("boots", "feet");
    for (ArmorMaterialList armor : ArmorMaterialList.values()) {
      for (ArmorItem.Type type : ArmorItem.Type.values()) {
        RegistryObject<ArmorItem> obj = DeferredRegisters.ITEMS.register(
            mappings.get(type.getName()) + armor.getName().replace(References.ID + ":", ""),
            () -> new ArmorItem(armor, type, new Properties()));
        DeferredRegisters.items.add(() -> obj.get());
      }
    }
    DeferredRegisters.CREATIVE_TABS.register("itemgroupextendedtools",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.itemgroupextendedtools"))
            .icon(() -> new ItemStack(DeferredRegisters.icon.get())).build());
  }
}
