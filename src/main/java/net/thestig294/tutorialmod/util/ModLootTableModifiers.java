package net.thestig294.tutorialmod.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.TimeCheckLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.item.ModItems;

public class ModLootTableModifiers {
    private static final Identifier JUNGLE_TEMPLE_ID = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier CREEPER_ID = new Identifier("minecraft", "entities/creeper");

    public static void modifyLootTables(){
//        Consider LootTableEvents.MODIFY.register() to be like an "OnLootTableQueried" hook
//        *See the VanillaEntityLootTableGenerator class for examples!*
//        *Or VanillaBlockLootTableGenerator!*

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id,
                                          tableBuilder, lootTableSource) -> {
            if (JUNGLE_TEMPLE_ID.equals(id)){
                LootPool.Builder metalDetectorLoot = LootPool.builder()
//                        Rolls = .rolls(), Number of times this loot pool is pulled from when triggered
                        .rolls(ConstantLootNumberProvider.create(1))
//                        Condition = .conditionally(), Pre-built conditions for this loot to spawn (See LootConditionTypes class!)
                        .conditionally(RandomChanceLootCondition.builder(1.0f))
//                        Entry = .with(), What information to pull from to decide what item to drop
//                        E.g. a TagEntry lets any item with that tag drop, an ItemEntry only drops one specific item
//                        (See LootPoolEntryTypes class!)
                        .with(ItemEntry.builder(ModItems.METAL_DETECTOR))
//                        Function = .apply(), What to do after it is decided the item will drop
//                        E.g. Change how many of the item drops from the default of 1 using SetCountLootFunction
//                        E.g. Auto-smelt the item, like hitting a cow with fire aspect, using FurnaceSmeltLootFunction
//                        (See LootFunctionTypes class!)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)));
//                Here, 0 to 1 metal detectors will drop.

//                The .build call here is optional, but the proper way to do it is: tableBuilder.pool(metalDetectorLoot.build());
                tableBuilder.pool(metalDetectorLoot);
            }

            if (CREEPER_ID.equals(id)) {
//                Creepers only drop 0-5 raw rubies during the first 1000 ticks of the day (/time set 0, /time set 1000)
                LootPool.Builder rawRubyLoot = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.RAW_RUBY))
                        .conditionally(TimeCheckLootCondition.create(BoundedIntUnaryOperator.create(0,1000)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f,5.0f)));

                tableBuilder.pool(rawRubyLoot);

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModItems.COAL_BRIQUETTE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)));

                tableBuilder.pool(poolBuilder);
            }
        }));
    }
}
