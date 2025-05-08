package io.github.dropxel.wildfood.generic.item;

import io.github.dropxel.wildfood.generic.WildFoodGenericMod;
import io.github.dropxel.wildfood.generic.item.ingredient.Ingredients;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WildFoodGenericItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WildFoodGenericMod.MOD_ID);
    public static final Map<String, DeferredHolder<Item, ? extends Item>> ITEM_MAP = Stream.of(Ingredients.values())
            .map(ingredient -> ITEMS.register(ingredient.getName(), ingredient::toItem))
            .collect(Collectors.toMap(Holder::getRegisteredName, item -> item));

    public static void
    register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
