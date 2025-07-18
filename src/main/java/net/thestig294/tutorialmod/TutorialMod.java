package net.thestig294.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.block.entity.ModBlockEntities;
import net.thestig294.tutorialmod.entity.ModBoats;
import net.thestig294.tutorialmod.entity.ModEntities;
import net.thestig294.tutorialmod.entity.custom.PorcupineEntity;
import net.thestig294.tutorialmod.item.ModItemGroups;
import net.thestig294.tutorialmod.item.ModItems;
import net.thestig294.tutorialmod.recipe.ModRecipes;
import net.thestig294.tutorialmod.screen.ModScreenHandlers;
import net.thestig294.tutorialmod.sound.ModSounds;
import net.thestig294.tutorialmod.util.ModCustomTrades;
import net.thestig294.tutorialmod.util.ModLootTableModifiers;
import net.thestig294.tutorialmod.villager.ModVillagerProfession;
import net.thestig294.tutorialmod.world.gen.ModWorldGeneration;
import net.thestig294.tutorialmod.world.tree.ModTrunkPlacerTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

//	This is essentially like an autorun file from Lua,
//	but instead of running files, you're running "main()" functions	from classes!
	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModLootTableModifiers.modifyLootTables();
		ModCustomTrades.registerCustomTrades();

		ModVillagerProfession.registerVillagers();
		ModSounds.registerSounds();

		ModEntities.registerModEntities();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();
		ModTrunkPlacerTypes.register();

//		You might want to separate FuelRegistry calls into a separate class in practice
//		(This is the equivalent to shoving everything into the autorun file in Lua...)
//		200 = 1 item smelted
		FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);
		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());

		StrippableBlockRegistry.register(ModBlocks.CHESTNUT_LOG, ModBlocks.STRIPPED_CHESTNUT_LOG);
		StrippableBlockRegistry.register(ModBlocks.CHESTNUT_WOOD, ModBlocks.STRIPPED_CHESTNUT_WOOD);

//		For vanilla burnChance and spreadChance values, look at: FireBlock.registerDefaultFlammables() for examples!
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_CHESTNUT_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_CHESTNUT_WOOD, 5, 5);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_LEAVES, 30, 60);

		ModBoats.registerBoats();
		ModWorldGeneration.generateModWorldGen();
	}
}