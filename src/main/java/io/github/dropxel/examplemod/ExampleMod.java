package io.github.dropxel.examplemod;

import com.mojang.logging.LogUtils;
import io.github.dropxel.examplemod.item.ExampleModItems;
import io.github.dropxel.examplemod.tab.ExampleModTabs;
import io.github.dropxel.examplemod.util.ExampleModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
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

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ExampleMod.MOD_ID)
public class ExampleMod {
    // Define provider id in a common place for everything to reference
    public static final String PROVIDER_ID = "Dropxel";
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "examplemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically
    public ExampleMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register our mod's items to the event bus
        ExampleModItems.register(modEventBus);

        // Register our mod's tabs to the event bus
        ExampleModTabs.register(modEventBus);

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
            if (Config.logDirtBlock) {
                LOGGER.info("\n\nDIRT BLOCK >> {}\n", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
            }
            LOGGER.info("\n\n{}{}\n", Config.magicNumberIntroduction, Config.magicNumber);
            Config.items.forEach((item) -> LOGGER.info("\nITEM >> {}\n", item.toString()));
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
            // Do something when the creative mode tab is opened
            LOGGER.info("\n\nHELLO FROM CREATIVE MODE TAB\n");
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
                        "\n\nREGISTERED EXAMPLEMOD ITEM TAGS >> {}\n",
                        clientLevel.registryAccess()
                                .registryOrThrow(Registries.ITEM)
                                .getTag(ExampleModTags.Items.EXAMPLE_MOD_ITEM)
                );
                LOGGER.info(
                        "\n\nREGISTERED EXAMPLEMOD ENCYCLOPEDIA ITEM TAGS >> {}\n",
                        clientLevel.registryAccess()
                                .registryOrThrow(Registries.ITEM)
                                .getTag(ExampleModTags.Items.EXAMPLE_MOD_ENCYCLOPEDIA_ITEM)
                );
                LOGGER.info(
                        "\n\nREGISTERED EXAMPLEMOD TAB TAGS >> {}\n",
                        clientLevel.registryAccess()
                                .registryOrThrow(Registries.CREATIVE_MODE_TAB)
                                .getTag(ExampleModTags.Tabs.EXAMPLE_MOD_CREATIVE_MODE_TAB_TAG)
                );
            }

            if (event.getEntity() instanceof ServerPlayer player) {
                LOGGER.info("\n\nPLAYER LOGGED IN >> {}\n", player.getDisplayName().getString());

                // Add our mod's encyclopedia to the player inventory only on the first login
                CompoundTag root = player.getPersistentData();
                CompoundTag persist = root.contains(Player.PERSISTED_NBT_TAG) ? root.getCompound(Player.PERSISTED_NBT_TAG) : new CompoundTag();
                String GOT_ENCYCLOPEDIA = PROVIDER_ID + MOD_ID + ":got_encyclopedia";
                if (!persist.getBoolean(GOT_ENCYCLOPEDIA)) {
                    player.getInventory().add(new ItemStack(ExampleModItems.ENCYCLOPEDIA_ITEM.get()));
                    persist.putBoolean(GOT_ENCYCLOPEDIA, true);
                    root.put(Player.PERSISTED_NBT_TAG, persist);
                }
            }
        }
    }
}
