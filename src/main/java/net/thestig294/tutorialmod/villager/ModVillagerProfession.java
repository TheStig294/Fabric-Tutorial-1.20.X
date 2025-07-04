package net.thestig294.tutorialmod.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.block.ModBlocks;

// FIXING BROKEN ZOMBIE VILLAGER TEXTURE
// ------------------------------------
// When a custom villager is zombified, Minecraft looks for a matching zombie villager texture.
// If the texture is missing, the model appears broken.
//
// ✅ FIX:
// 1. Go to your resource pack folder: assets/myendmod/textures/entity/
// 2. Create a new folder named: `zombie_villager/`
// 3. Inside `zombie_villager/`, create another folder called `profession/`
// 4. Place your villager's zombie texture inside `profession/` and name it correctly.
//    Example: assets/myendmod/textures/entity/zombie_villager/profession/end_trader.png
//
// 5. Reload textures in-game with F3+T or restart Minecraft.
//
// Now, your custom villager will have the correct zombie villager texture when infected!

public class ModVillagerProfession {
//    Some blocks already have a POI associated with them in the PointOfInterestTypes class,
//    if so, it isn't possible to register another profession with that block, as it already has been registered as a POI!
//    Instead, you need to create a custom block inheriting from the block you want to add the villager profession for, and
//    set that block as a POI instead!

    public static final RegistryKey<PointOfInterestType> SOUND_POI_KEY = poiKey("soundpoi");
    @SuppressWarnings("unused")
    public static final PointOfInterestType SOUND_POI = registerPoi("soundpoi", ModBlocks.SOUND_BLOCK);

    public static final VillagerProfession SOUND_MASTER = registerProfession("sound_master", SOUND_POI_KEY);


    @SuppressWarnings("SameParameterValue")
    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type){
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(TutorialMod.MOD_ID, name),
//                See the VillagerProfession class for what all of this is...
//                Basically setting an id, making it so this profession only uses the profession workstation from this type of profession
//                and setting whether a villager with this profession can gather items, can use any other workstations
//                and setting the sound a villager working with the profession makes
                new VillagerProfession(name, entry -> entry.matchesKey(type),
                        entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_SHEPHERD));
    }

    @SuppressWarnings("SameParameterValue")
    private static PointOfInterestType registerPoi(String name, Block block){
//        "ticketCount" is the number of villagers that can get a profession from 1 workstation block
//        most workstations have only 1 ticket
        return PointOfInterestHelper.register(new Identifier(TutorialMod.MOD_ID, name), 1, 1, block);
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryKey<PointOfInterestType> poiKey(String name){
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(TutorialMod.MOD_ID, name));
    }

//    Java quirk to "poke" the class and instantiate all the static data members
    public static void registerVillagers(){
        TutorialMod.LOGGER.info("Registering Villagers " + TutorialMod.MOD_ID);
    }
}
