package com.enderdevelopment.piedraparlante;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("piedraparlante")
public class PiedraParlanteMod {
    private static final String MODID = "piedraparlante";
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    private static final RegistryObject<EntityType<PiedraParlanteEntity>> PIEDRA_PARLANTE = ENTITY_TYPES.register("piedra_parlante", () -> EntityType.Builder.of(PiedraParlanteEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build("piedra_parlante"));
    
    public PiedraParlanteMod() {
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
        private static int stoneBlocksMined = 0;

        @SubscribeEvent
        public static void onBlockBreak(PlayerEvent.BreakSpeed event) {
            if (event.getState().getBlock() == Blocks.STONE) {
                stoneBlocksMined++;
                if (stoneBlocksMined == 8) {
                    PiedraParlanteEntity piedraParlante = PIEDRA_PARLANTE.get().create(event.getEntity().level());
                    piedraParlante.setPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                    event.getEntity().level().addFreshEntity(piedraParlante);
                    event.getEntity().sendSystemMessage(Component.literal("¡Auch! ¡Auch!"));
                    stoneBlocksMined = 0;
                }
            }
        }
    }
}