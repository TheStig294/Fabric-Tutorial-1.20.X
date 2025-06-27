package net.thestig294.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.item.ModItemGroups;
import net.thestig294.tutorialmod.item.ModItems;
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

//		You might want to separate FuelRegistry calls into a separate class in practice
//		(This is the equivalent to shoving everything into the autorun file in Lua...)
//		200 = 1 item smelted
		FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);
	}
}