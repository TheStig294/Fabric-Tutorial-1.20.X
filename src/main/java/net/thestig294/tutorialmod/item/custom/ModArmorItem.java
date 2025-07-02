package net.thestig294.tutorialmod.item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.item.ModArmorMaterials;

import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, StatusEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterials.RUBY, new StatusEffectInstance(
                            StatusEffects.HASTE, 400, 1, false,false,true))
                    .build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()){
//            Looks like "[obj] instanceof [class castedObj]" is how you check if an object is of a certain type in Java...
//            Also, this is wildly shorthand for Java lol... This is creating a cast copy of "entity", and using it
//            in the same if statement... (Does the PlayerEntity(Entity) constructor get called?)
            if (entity instanceof PlayerEntity player && hasFullSuitOfArmorOn(player)){
                evaluateArmorEffects(player);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player){
//        for (int i = 0; i <= 3; i++){
//            if (player.getInventory().getArmorStack(i).isEmpty()){
//                return false;
//            }
//        }
//
//        return true;
//
//        That is how I would write it, but just for demonstrative purposes:
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private void evaluateArmorEffects(PlayerEntity player){
//        (Probably better to use "var" here...)
        for (Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()){
//            (Surely Java has something like static binds in C++...)
            ArmorMaterial mapArmorMaterial = entry.getKey();
            StatusEffectInstance mapStatusEffect = entry.getValue();

            if (hasCorrectArmorOn(mapArmorMaterial, player)){
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, PlayerEntity player){
//        You can get an iterable array of items in a player's inventory!
//        player.getInventory().main, (Ordinary inventory slots. Includes hotbar, which is index 0-9)
//        (.isValidHotbarIndex(), .getSlotWithStack(), and other lovely functions exist! See the PlayerInventory class!)
//        player.getInventory().armor, and
//        player.getInventory().offhand (An array of size 1)

        for (ItemStack armorStack: player.getInventory().armor){
//            First check the player is indeed wearing armour in every slot, and not like a pumpkin or something...
//            Then check if the armor is the current material found in the map
            if (!(armorStack.getItem() instanceof ArmorItem armor) || armor.getMaterial() != material){
                return false;
            }
        }

        return true;
    }

    private void addStatusEffectForMaterial(PlayerEntity player, ArmorMaterial mapArmorMaterial, StatusEffectInstance mapStatusEffect){
        boolean hasPlayerEffect = player.hasStatusEffect(mapStatusEffect.getEffectType());

        if (!hasPlayerEffect && hasCorrectArmorOn(mapArmorMaterial, player)){
//            It's important to create a new StatusEffectInstance, else the status will apply once, then will stop working
            player.addStatusEffect(new StatusEffectInstance(mapStatusEffect));
        }
    }
}
