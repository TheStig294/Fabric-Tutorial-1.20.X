package net.thestig294.tutorialmod.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// Basically just grabs the TrunkPlacerType.register() function, and puts it into this class (Even though it is private!)

@Mixin(TrunkPlacerType.class)
public interface TrunkPlacerTypeInvoker {
    @Invoker("register")
    static <P extends TrunkPlacer> TrunkPlacerType<P> callRegister(String id, Codec<P> codec) {
//        The code inside a Mixin Invoker should NEVER be called!
//        (Instead, the function you are invoking should be called instead when you try to call this function!)
        throw new IllegalStateException();
    }
}
