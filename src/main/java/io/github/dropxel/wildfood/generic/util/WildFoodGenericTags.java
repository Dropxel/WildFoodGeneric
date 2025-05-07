package io.github.dropxel.wildfood.generic.util;

import io.github.dropxel.wildfood.generic.WildFoodGenericMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class WildFoodGenericTags {
    public static class Items {
        public static final TagKey<Item> EXAMPLE_MOD_ITEM = createTag("item");

        private static TagKey<Item>
        createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(WildFoodGenericMod.MOD_ID, name));
        }
    }
}
