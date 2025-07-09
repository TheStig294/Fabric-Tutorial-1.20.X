package net.thestig294.tutorialmod.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;

//
// Prepares a model ID, and declares separate models for a single entity (for complex entity models like the Enderdragon)
//

public class ModModelLayers {
//    This is basically just creating a link between the imported model class, and the type of class
//    EntityModelLayerRegistry.registerModelLayer() requires!
//    So now, TutorialModClient can register the porcupine model with the model registry, and create this link!
//    (This is basically just creating the ID!)
    public static final EntityModelLayer PORCUPINE =
            new EntityModelLayer(new Identifier(TutorialMod.MOD_ID, "porcupine"), "main");
}
