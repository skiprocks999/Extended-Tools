package extendedtools;

import extendedtools.common.item.Tags;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class ExtendedTools {

    public ExtendedTools(IEventBus bus) {
        Registers.ARMOR_MATERIALS.register(bus);
        Registers.ITEMS.register(bus);
        Registers.CREATIVE_TABS.register(bus);
        Tags.init();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void onLoadEvent(FMLLoadCompleteEvent event) {
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ExtendedToolsCreativeRegistry {

        @SubscribeEvent
        public static void registerItems(BuildCreativeModeTabContentsEvent event) {
            if (event.getTab() == Registers.MAIN.get()) {
                Registers.ITEMS.getEntries().forEach(reg -> {
                    event.accept(reg.get());
                });
            }
        }
    }
}
