package net.thestig294.tutorialmod.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//            The mess here is mostly due to the ItemRenderer class having a local variable called "models",
//            which contains the core model atlas for Minecraft.
//            So, we needed to create a hooked "accessor" method (called getItemRenderer$models()),
//            just to access the stored model object of the 3D ruby staff!

//            Now that we have a way of getting the model atlas from the ItemRendererAccessor class,
//            we can hack into Minecraft's core ItemRenderer.renderItem() function,
//            (which is the core function responsible for rendering all items in the game),
//            by hooking into it using a "ModifyVariable"-type hook.

//            "ModifyVariable" lets you change the value of an argument to a function, and expects you to move the arg
//            you want to change to be the first argument.
//            You can access other args, but they are just passed by value.
//            You then need to return the modified variable, which then modifies the value of the arg.

//            So, by accessing the stored 3D ruby staff model in Minecraft's model atlas, (given by getItemRenderer$models()),
//            we're able to return the 3D model instead of the oldModel (the 2D model), whenever we're not looking at
//            the ruby staff in the inventory.
//            Thus the .renderItem() function gets its "model" argument changed,
//            and since we're hooking into the function at its start (@At("HEAD")), it now begins rendering the 3D model!

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
//    IDK how to fix the properly... I think if a 3D-in-hand, 2D-in-world item is ever needed, it's best just to look it up...
    @SuppressWarnings("AmbiguousMixinReference")
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useRubyStaffModel(BakedModel oldModel, ItemStack stack, ModelTransformationMode renderMode,
                                        boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                        int light, int overlay){

//        If the item is not being rendered in the GUI, then return the new 3D model of the ruby staff
        if (stack.isOf(ModItems.RUBY_STAFF) &&
                renderMode != ModelTransformationMode.GUI &&
                renderMode != ModelTransformationMode.FIXED &&
                renderMode != ModelTransformationMode.GROUND){
            return ((ItemRendererAccessor) this).getItemRenderer$models().getModelManager().getModel(
                    new ModelIdentifier(TutorialMod.MOD_ID, "ruby_staff_3d", "inventory"));
        }

        return oldModel;
    }
}