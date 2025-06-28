package net.thestig294.tutorialmod.mixin;

import net.thestig294.tutorialmod.TutorialMod;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

// *Remember for any mixin class file you create, you need to add it to the "[modname].mixins.json" file in your "resources" folder!*

// Inside the "ModelLoader" class,
@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

//    Prepare to use the class's "addModel()" function
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

//    Then hook into ModelLoader's constructor, (method = "<init>")
//    and also any function calling ModelLoader's constructor, or ModelIdentifier's constructor, (@At(value = "INVOKE"...),
//    and call the hook function *after* the constructors are called. (shift = At.Shift.AFTER)
//    (Unfortunately... there's 1 more janky detail... ordinal = 3 means we only hook onto the 3rd time *ANY* of these
//    constructors are called... So I think the only way to know how to do 3D-in-hand, 2D-in-inventory models is to look it up...)

    @Inject(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
            ordinal = 3, shift = At.Shift.AFTER))
    public void addRubyStaff(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
                             Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
//        The 3D staff model then gets added to the "inventory" pool of models, which is used when you are holding an item
//        (or are looking at an item in your inventory)
        this.addModel(new ModelIdentifier(TutorialMod.MOD_ID, "ruby_staff_3d", "inventory"));
    }
}