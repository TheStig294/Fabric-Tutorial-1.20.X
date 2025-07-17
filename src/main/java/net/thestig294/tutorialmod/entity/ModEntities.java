package net.thestig294.tutorialmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.entity.custom.DiceProjectileEntity;
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

    public static final EntityType<DiceProjectileEntity> DICE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(TutorialMod.MOD_ID, "dice_projectile"),
//            Weird Java generics casting here... "FabricEntityTypeBuilder.<DiceProjectileEntity>create()"
//            This function's signature is: public static <T extends Entity> FabricEntityTypeBuilder<T> create()
//            and as you can see... It returns a type that extends Entity
//            however, ThrownItemEntity's constructor demands a type that extends ThrownItemEntity:
//            DiceProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, ...)
//            And Entity does not extend ThrownItemEntity! (It's the other way around!)
//            So, we have to cast the returned EntityType<Entity> into a EntityType<ThrownItemEntity>,
//            so DiceProjectileEntity's constructor is happy! (And this is the weird Java syntax to do it...)
            FabricEntityTypeBuilder.<DiceProjectileEntity>create(SpawnGroup.MISC, DiceProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

    public static void registerModEntities() {
        TutorialMod.LOGGER.info("Registering Entities for " + TutorialMod.MOD_ID);
    }
}
