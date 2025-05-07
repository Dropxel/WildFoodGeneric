package io.github.dropxel.wildfood.generic.item;

import io.github.dropxel.wildfood.generic.WildFoodGenericMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;

public class WildFoodGenericItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(WildFoodGenericMod.MOD_ID);
    public static final Collection<DeferredHolder<Item, ? extends Item>> ITEM_COLLECTION = ITEMS.getEntries();

    public static void
    register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
