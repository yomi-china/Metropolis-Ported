package team.dovecotmc.metropolis;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.dovecotmc.metropolis.block.MetroBlocks;
import team.dovecotmc.metropolis.block.entity.MetroBlockEntities;
import team.dovecotmc.metropolis.config.MetroConfig;
import team.dovecotmc.metropolis.entity.EntitySittable;
import team.dovecotmc.metropolis.entity.MetroEntities;
import team.dovecotmc.metropolis.item.MetroItems;
import team.dovecotmc.metropolis.network.MetroServerNetwork;
import team.dovecotmc.metropolis.sittable.SittableRegistries;
import team.dovecotmc.metropolis.sittable.SittableRegistry;

import java.util.Optional;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2023 Arrokoth All Rights Reserved.
 */
public class Metropolis implements ModInitializer {
    public static final String MOD_ID = "metropolis";
    public static final Logger LOGGER = LogManager.getLogger("Metropolis");

    public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder(new ResourceLocation(MOD_ID, "all"))
            .icon(() -> new ItemStack(MetroItems.ITEM_ITV_MONITOR))
            .title(net.minecraft.network.chat.Component.translatable("itemGroup.metropolis.all"))
            .build();

    public static final MetroConfig config = MetroConfig.load();

    @Override
    public void onInitialize() {
        MetroBlocks.initialize();
        MetroBlockEntities.initialize();
        MetroEntities.initialize();
        MetroItems.initialize();
        MetroServerNetwork.registerAll();
        SittableRegistries.registerSittable(new SittableRegistry(MetroBlocks.BLOCK_BENCH, (state, player, hit) -> Optional.of(new Vec3(0.5, 0.1, 0.5))));
//        MetroEnumUtil.addRailtype("rail_5", 5, MapColor.BLUE, false, true, true, RailType.RailSlopeStyle.CURVE);

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> {
            entries.accept(MetroItems.ITEM_CABLE);
            entries.accept(MetroItems.ITEM_TICKET_VENDOR_TOP);
            entries.accept(MetroItems.ITEM_TICKET_VENDOR_PANEL);
            entries.accept(MetroItems.ITEM_TICKET_VENDOR_EM10);
            entries.accept(MetroItems.ITEM_TICKET_VENDOR_EV23);
            entries.accept(MetroItems.ITEM_FARE_ADJ_EV23);
            entries.accept(MetroItems.ITEM_CEILING_A);
            entries.accept(MetroItems.ITEM_FLUORESCENT_LAMP);
            entries.accept(MetroItems.ITEM_BUMPER);
            entries.accept(MetroItems.ITEM_CAMERA);
            entries.accept(MetroItems.ITEM_TURNSTILE_ENTER);
            entries.accept(MetroItems.ITEM_TURNSTILE_EXIT);
            entries.accept(MetroItems.ITEM_TURNSTILE_IC_ONLY_ENTER);
            entries.accept(MetroItems.ITEM_TURNSTILE_IC_ONLY_EXIT);
            entries.accept(MetroItems.ITEM_SIGN_NO_PHOTO);
            entries.accept(MetroItems.ITEM_PSD_JR_DOOR_1);
            entries.accept(MetroItems.ITEM_PSD_JR_DOOR_2);
            entries.accept(MetroItems.ITEM_PSD_JR_FENCE_1);
            entries.accept(MetroItems.ITEM_PSD_JR_FENCE_2);
            entries.accept(MetroItems.ITEM_SINGLE_TRIP_TICKET);
            entries.accept(MetroItems.ITEM_CARD);
            entries.accept(MetroItems.ITEM_CREATIVE_CARD);
            entries.accept(MetroItems.ITEM_EXIT_TICKET);
            entries.accept(MetroItems.ITEM_ITV_MONITOR);
            entries.accept(MetroItems.ITEM_SECURITY_DOOR);
            entries.accept(MetroItems.ITEM_SECURITY_INSPECTION_MACHINE);
            entries.accept(MetroItems.ITEM_CONCRETE);
            entries.accept(MetroItems.ITEM_PLATFORM_A);
            entries.accept(MetroItems.ITEM_TILES_WHITE);
            entries.accept(MetroItems.ITEM_TILES_LARGE_WHITE);
            entries.accept(MetroItems.ITEM_TILES_HORIZONTAL_WHITE);
            entries.accept(MetroItems.ITEM_TILES_SMALL_WHITE);
            entries.accept(MetroItems.ITEM_TILES_GRAY);
            entries.accept(MetroItems.ITEM_TILES_HORIZONTAL_GRAY);
            entries.accept(MetroItems.ITEM_TILES_SMALL_GRAY);
            entries.accept(MetroItems.ITEM_CORDON_YELLOW_BLACK);
            entries.accept(MetroItems.ITEM_CORDON_YELLOW_WHITE);
            entries.accept(MetroItems.ITEM_CORDON_RED_BLACK);
            entries.accept(MetroItems.ITEM_CORDON_RED_WHITE);
            entries.accept(MetroItems.ITEM_BENCH);
            entries.accept(MetroItems.ITEM_AWNING_PILLAR);
            entries.accept(MetroItems.ITEM_AWNING_PILLAR_EMERGENCY);
            entries.accept(MetroItems.ITEM_AWNING_BEAM);
            entries.accept(MetroItems.ITEM_AWNING_ROOF);
        });

        // TODO: Ask Haruka: Japanese localization!!!

        UseBlockCallback.EVENT.register(
                (player, world, hand, hitResult) -> !player.isShiftKeyDown() && EntitySittable.trySit(world, hitResult.getBlockPos(), world.getBlockState(hitResult.getBlockPos()), hitResult, player) ?
                        InteractionResult.SUCCESS :
                        InteractionResult.PASS
        );
    }
}