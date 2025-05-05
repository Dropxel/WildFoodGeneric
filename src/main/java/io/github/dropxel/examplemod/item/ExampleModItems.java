package io.github.dropxel.examplemod.item;

import io.github.dropxel.examplemod.ExampleMod;
import io.github.dropxel.examplemod.item.encyclopedia.ExampleModEncyclopediaItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.WrittenBookItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;

public class ExampleModItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MOD_ID);
    public static final Collection<DeferredHolder<Item, ? extends Item>> ITEM_COLLECTION = ITEMS.getEntries();
    public static final DeferredItem<WrittenBookItem> ENCYCLOPEDIA_ITEM = ITEMS.registerItem(
            ExampleModEncyclopediaItem.ENCYCLOPEDIA_ITEM_ID,
            props -> new WrittenBookItem(props.stacksTo(1))
    );

    public static void
    register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
