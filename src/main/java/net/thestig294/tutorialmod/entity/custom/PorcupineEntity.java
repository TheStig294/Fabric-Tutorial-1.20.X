package net.thestig294.tutorialmod.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.entity.ModEntities;
import net.thestig294.tutorialmod.entity.ai.PorcupineAttackGoal;
import org.jetbrains.annotations.Nullable;

//
// Implements the behaviour of the entity
//

// How to know which class to extend when making a new entity?
// Find Minecraft's "Entity" class, then Ctrl-H on it, and expand all, to see ALL of Minecraft's entities to extend!
// In our case, the most appropriate is the "AnimalEntity" class
// (But... say you could look at or extend the ArrowEntity or FireballEntity to make explosive arrows!)

// (Look at the CamelEntity for an example on how animations work!)

public class PorcupineEntity extends AnimalEntity {
//    The TrackedData class lets you set global data between the server and client on an entity!
//    (Just like ent:SetNWBool/String/Entity etc. in Gmod!)

//    *Whenever you use a TrackedData object, ALWAYS implement the "initDataTracker()" function!* (as below)
    private static final TrackedData<Boolean> ATTACKING =
        DataTracker.registerData(PorcupineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public PorcupineEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

//    (Copied from CamelEntity's updateAnimations() method)
    private void setupAnimationStates(){
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            this.idleAnimationTimeout--;
        }

        if (this.isAttacking() && attackAnimationTimeout <= 0) {
//            For 40 seconds...
            attackAnimationTimeout = 40;
//            ...play the attack animation!
//            (Basically ent:ResetSequence("attack") from Gmod!)
            attackAnimationState.start(this.age);
        } else {
            --this.attackAnimationTimeout;
        }

        if (!this.isAttacking()) {
//            Looks like you don't need to trigger the idle animation to stop an animation like in Gmod,
//            you can outright stop animations!
            attackAnimationState.stop();
        }
    }

//    Just copied from the CamelEntity class lol
//    We already have an entity "pose" (somehow...)
    @Override
    protected void updateLimbs(float posDelta) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(posDelta * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient){
            setupAnimationStates();
        }
    }

    //    Goals = Mob AI
//    The WolfEntity is a great example of different goalSelectors and targetSelectors available to you!
    @Override
    protected void initGoals() {
//        Note: Typically, if you don't put a SwimGoal as the first priority goal for your mob, it will just sink and drown in water lol
        this.goalSelector.add(0, new SwimGoal(this));

//        To find all goals available, just Ctrl-H on Minecraft's "Goal" object!
//        (Don't know what the "pauseWhenMobIdle" bool actually does... just set it to "true" and your custom attack animation should work!)
        this.goalSelector.add(1, new PorcupineAttackGoal(this, 1D, true));

        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.BEETROOT), false));

        this.goalSelector.add(3, new FollowParentGoal(this, 1.15D));

        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(3, new LookAroundGoal(this));

//        The "RevengeGoal" class basically sets the mob to just attack whatever attacked it, and nothing else.
        this.targetSelector.add(1, new RevengeGoal(this));
    }

//    Attributes = The Entity-equivalent to settings from Blocks/Items!
//    (They need to be registered with a call to the base mod's main function, i.e. in the TutorialMod class)
    public static DefaultAttributeContainer.Builder createPorcupineAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
    }

    public void setAttacking(boolean attacking){
        this.dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.BEETROOT);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PORCUPINE.create(world);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }
}
