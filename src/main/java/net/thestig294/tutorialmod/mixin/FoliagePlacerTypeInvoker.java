package net.thestig294.tutorialmod.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerTypeInvoker {
//    The @Invoker annotation is so smart, it will auto-strip the name of an invoker function starting with "call"
//    turn the capital letter to a lowercase letter, and look for a function with that name!
//    (In this case callRegister -> register, so @Invoker looks for a function called "register"!)
    @Invoker
    static <P extends FoliagePlacer> FoliagePlacerType<P> callRegister(String id, Codec<P> codec) {
        throw new IllegalStateException();
    }
}
