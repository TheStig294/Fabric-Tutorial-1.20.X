package net.thestig294.tutorialmod.mixin;

import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//    If a class is an interface, and contains only @Accessor or @Invoker mixin methods, then a special behaviour happens.
//    The class becomes a magic way of accessing local variables within the specified class!
//    You can define "getter" methods using @Accessor, and "setter" methods using @Invoker.
//    *This only works with local (private) variables!* (You can just modify the variable yourself as a global otherwise...)

//    In order to get @Accessor variables, you need to create a mixin hook, and call the @Accessor method within it
//    in a weird way, by casting the hook's class... E.g. "((ItemRendererAccessor) this).getItemRenderer$models()"
//    (See ItemRendererMixin for more details...)

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
    @Accessor("models")
    ItemModels getItemRenderer$models();
}
