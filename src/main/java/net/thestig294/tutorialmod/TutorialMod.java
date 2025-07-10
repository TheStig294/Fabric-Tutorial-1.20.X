package net.thestig294.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.block.entity.ModBlockEntities;
import net.thestig294.tutorialmod.entity.ModEntities;
import net.thestig294.tutorialmod.entity.custom.PorcupineEntity;
import net.thestig294.tutorialmod.item.ModItemGroups;
import net.thestig294.tutorialmod.item.ModItems;
import net.thestig294.tutorialmod.screen.ModScreenHandlers;
import net.thestig294.tutorialmod.sound.ModSounds;
import net.thestig294.tutorialmod.util.ModCustomTrades;
import net.thestig294.tutorialmod.util.ModLootTableModifiers;
import net.thestig294.tutorialmod.villager.ModVillagerProfession;
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

//		You might want to separate FuelRegistry calls into a separate class in practice
//		(This is the equivalent to shoving everything into the autorun file in Lua...)
//		200 = 1 item smelted
		FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);
		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());
	}
}