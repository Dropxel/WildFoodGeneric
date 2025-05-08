package io.github.dropxel.wildfood.generic;

import com.mojang.logging.LogUtils;
import io.github.dropxel.wildfood.generic.item.WildFoodGenericItems;
import io.github.dropxel.wildfood.generic.util.WildFoodGenericTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import net.minecraft.world.item.CreativeModeTabs;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(WildFoodGenericMod.MOD_ID)
public class WildFoodGenericMod {
    // Define provider id in a common place for everything to reference
    public static final String PROVIDER_ID = "Dropxel";
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "wildfood_generic";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically
    public WildFoodGenericMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register our mod's items to the event bus
        WildFoodGenericItems.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        // You can use SubscribeEvent and let the Event Bus discover methods to call
        @SubscribeEvent
        public static void
        onCommonSetup(final FMLCommonSetupEvent event) {
            // Some common setup code
            LOGGER.info("\n\nHELLO FROM COMMON SETUP\n");
        }

        @SubscribeEvent
        public static void
        onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("\n\nHELLO FROM CLIENT SETUP\n");
            LOGGER.info("\n\nMINECRAFT NAME >> {}\n", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void
        onCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                WildFoodGenericItems.ITEM_MAP.values().forEach(holder -> event.accept(holder.get()));
            }
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents {
        @SubscribeEvent
        public static void
        onServerStarting(ServerStartingEvent event) {
            // Do something when the server starts
            LOGGER.info("\n\nMINECRAFT SERVER STARTING\n");
        }

        @SubscribeEvent
        public static void
        onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            // Do something when a player logs into the server
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (clientLevel != null) {
                LOGGER.info(
                        "\n\nREGISTERED WILDFOOD GENERIC ITEM TAGS >> {}\n",
                        clientLevel.registryAccess()
                                .registryOrThrow(Registries.ITEM)
                                .getTag(WildFoodGenericTags.Items.EXAMPLE_MOD_ITEM)
                );
            }

            if (event.getEntity() instanceof ServerPlayer player) {
                LOGGER.info("\n\nPLAYER LOGGED IN >> {}\n", player.getDisplayName().getString());
            }
        }
    }
}
