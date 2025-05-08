package io.github.dropxel.wildfood.generic.item.ingredient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.item.Item;

import static io.github.dropxel.wildfood.generic.WildFoodGenericMod.MOD_ID;

@Getter
@RequiredArgsConstructor
public enum Ingredients {
    CHOCOLATE_SPREAD("chocolate_spread"),
    CONDENSED_MILK("condensed_milk"),
    DOUGH("dough"),
    FLOUR("flour"),
    MUSHROOM_POWDER("mushroom_powder"),
    POTATO_STARCH("potato_starch"),
    RABBIT_ESSENCE("rabbit_essence"),
    SALT("salt"),
    SPICY_MUSHROOM_POWDER("spicy_mushroom_powder");

    private final String name;

    public String getTag() {
        return MOD_ID + ":" + name;
    }

    public Item toItem() {
        return new Item(new Item.Properties());
    }
}
