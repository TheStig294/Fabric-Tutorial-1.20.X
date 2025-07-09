package net.thestig294.tutorialmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.entity.custom.PorcupineEntity;

//
// Registers entities
//

public class ModEntities {
    public static final EntityType<PorcupineEntity> PORCUPINE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(TutorialMod.MOD_ID, "porcupine"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PorcupineEntity::new)
//                    The hitbox of the entity is 1 block by 1 block
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build());

    public static void registerModEntities() {
        TutorialMod.LOGGER.info("Registering Entities for " + TutorialMod.MOD_ID);
    }
}
