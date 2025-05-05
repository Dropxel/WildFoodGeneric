package io.github.dropxel.examplemod.tab;

import io.github.dropxel.examplemod.ExampleMod;
import io.github.dropxel.examplemod.item.ExampleModItems;
import io.github.dropxel.examplemod.tab.creative_mode_tab.ExampleModCreativeModeTab;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ExampleModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_MOD_CREATIVE_MODE_TAB =
            CREATIVE_MODE_TABS.register(ExampleModCreativeModeTab.CREATIVE_MODE_TAB_ID, () -> CreativeModeTab.builder()
                    .title(Component.translatable(Util.makeDescriptionId(
                            "itemGroup",
                            ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, ExampleModCreativeModeTab.CREATIVE_MODE_TAB_ID)
                    )))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ExampleModItems.ENCYCLOPEDIA_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> ExampleModItems.ITEM_COLLECTION.stream()
                            .map(DeferredHolder::get)
                            .forEach(output::accept)
                    ).build()
            );

    public static void
    register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
