package net.thestig294.tutorialmod.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import net.thestig294.tutorialmod.entity.custom.PorcupineEntity;

public class PorcupineAttackGoal extends MeleeAttackGoal {
    private final PorcupineEntity entity;
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;

    public PorcupineAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = (PorcupineEntity) mob;
    }

    @Override
    public void start() {
        super.start();
//        These numbers are specific to the attack animation length from the ModAnimations.PORCUPINE_ATTACK animation
//        PORCUPINE_ATTACK = Animation.Builder.create(2f), which means the animation is 2 seconds long.
//        We also know the attack should happen 1 second into the animation.
//        So, since 20 ticks = 1 second, we wait 20 ticks (1 second) before applying the attack!
        attackDelay = 20;
        ticksUntilNextAttack = 20;
    }

    @Override
    protected void attack(LivingEntity pEnemy) {
        if (isEnemyWithinAttackDistance(pEnemy)){
            shouldCountTillNextAttack = true;

            if (isTimeToStartAttackAnimation()){
                entity.setAttacking(true);
            }

            if (isTimeToAttack()) {
                this.mob.getLookControl().lookAt(pEnemy.getX(), pEnemy.getY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy){
        return this.entity.distanceTo(pEnemy) <= 2f;
    }

//    Multiplying the attackDelay by 2 lets the actual attack damage occur 1 second into the animation,
//    since 20 ticks in, the attack occurs, and attackDelay = 20, and ticksUntilNextAttack = 0, then reset to = 40
//    and 40 ticks in, the animation completes, and attackDelay = 20, and ticksUntilNextAttack = 20,
//    and 60 ticks in, after the animation is halfway through looping again, ticksUntilNextAttack = 0 again,
//    and thus the attack is triggered with a 1-second delay again!
//
//    attackDelay controls how long the initial delay in the attack animation is before the attack is applied
//    ticksUntilNextAttack controls when it is time to trigger the next attack
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2);
    }

    protected boolean isTimeToStartAttackAnimation(){
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected boolean isTimeToAttack(){
        return this.ticksUntilNextAttack <= 0;
    }

    protected void performAttack(LivingEntity pEnemy){
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
//        Keeps track of how many ticks have passed until ticksUntilNextAttack reaches 0
        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
