package io.github.dropxel.examplemod.util;

import io.github.dropxel.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ExampleModTags {
    public static class Items {
        public static final TagKey<Item> EXAMPLE_MOD_ITEM = createTag("item");
        public static final TagKey<Item> EXAMPLE_MOD_ENCYCLOPEDIA_ITEM = createTag("encyclopedia");

        private static TagKey<Item>
        createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
        }
    }

    public static class Tabs {
        public static final TagKey<CreativeModeTab> EXAMPLE_MOD_CREATIVE_MODE_TAB_TAG = createTag("creative_mode_tab");

        private static TagKey<CreativeModeTab>
        createTag(String name) {
            return TagKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
        }
    }
}
