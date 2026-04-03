package net.dillon.survivalfly.mixin.main;

import com.mojang.authlib.GameProfile;
import net.dillon.survivalfly.util.ModTexts;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.dillon.survivalfly.util.ModUtil.*;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements PlayerAbilitiesExtension {
    @Shadow
    protected abstract boolean isPvpAllowed();
    @Unique
    private float elytraDurabilityProgress;
    @Unique
    private boolean everEnabledFlight = false;
    @Unique
    private boolean wantToFlyAgain;
    @Unique
    private boolean playedBroken;
    @Unique
    private int damageTimeTicks = 0;

    public ServerPlayerMixin(Level p_250508_, BlockPos p_250289_, float p_251702_, GameProfile p_252153_) {
        super(p_250508_, p_250289_, p_251702_, p_252153_);
    }

    /**
     * Sets the player if they have ever had flight before.
     */
    @Override
    public void setEverEnabledFlight(boolean value) {
        this.everEnabledFlight = value;
    }

    /**
     * Sets time until flight is allowed for the player again.
     */
    @Override
    public void setWantToFlyAgain(boolean value) {
        this.wantToFlyAgain = value;
    }

    /**
     * Sets the player's damage ticks.
     */
    @Override
    public void setDamageTicks(int value) {
        this.damageTimeTicks = value;
    }

    /**
     * @return if the player has ever had flight before.
     */
    @Override
    public boolean hasEverEnabledFlight() {
        return this.everEnabledFlight;
    }

    /**
     * @return if the player's flight is allowed.
     */
    @Override
    public boolean flyingAllowed() {
        return !options().friendlyFlight.enabled() || this.damageTimeTicks == 0;
    }

    /**
     * Writes player NBT data for flight.
     */
    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void saveDamageTime(CompoundTag output, CallbackInfo ci) {
        output.putBoolean(EVER_ENABLED_FLIGHT_NAME, this.everEnabledFlight);
        output.putBoolean(WANT_TO_FLY_AGAIN, this.wantToFlyAgain);
        output.putInt(DAMAGE_TIME_TICKS_NAME, this.damageTimeTicks);
        output.putBoolean(PLAYED_BROKEN_NAME, this.playedBroken);
    }

    /**
     * Reads player NBT data for flight.
     */
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readDamageTime(CompoundTag input, CallbackInfo ci) {
        this.everEnabledFlight = input.getBoolean(EVER_ENABLED_FLIGHT_NAME);
        this.wantToFlyAgain = input.getBoolean(WANT_TO_FLY_AGAIN);
        this.damageTimeTicks = input.getInt(DAMAGE_TIME_TICKS_NAME);
        this.playedBroken = input.getBoolean(PLAYED_BROKEN_NAME);
    }

    /**
     * Removes flight from player who started combat.
     */
    @Inject(method = "hurt", at = @At("HEAD"))
    private void onDamageDisableFlight(DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayer victimPlayer = (ServerPlayer)(Object)this;
        if (!options().friendlyFlight.enabled() || isInvalidPlayerGameMode(victimPlayer) || !isPvpAllowed()) {
            return;
        }

        Entity attacker = source.getEntity();

        if (!isInvalidPlayerGameMode(victimPlayer) && options().friendlyFlight.mobsAllowed() && attacker instanceof LivingEntity) {
            stopFlightForPlayer(victimPlayer, false);
        }

        if (attacker instanceof ServerPlayer attackerPlayer && !isInvalidPlayerGameMode(attackerPlayer)) {
            stopFlightForPlayer(attackerPlayer, true);
            stopFlightForPlayer(victimPlayer, false);
        }
    }

    /**
     * Adds restrictions to the player's flying abilities, based on certain options.
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void addSideEffects(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        if (isInvalidPlayerGameMode(player)) {
            return;
        }

        // Friendly flight functionality (stops player flight if taking damage)
        if (options().friendlyFlight.enabled()) {
            if (this.damageTimeTicks == -1) {
                this.damageTimeTicks = DEFAULT_DAMAGE_TIME_TICKS;
            }
            if (this.damageTimeTicks > 0) {
                if (player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
                if (this.wantToFlyAgain && this.damageTimeTicks == 1) {
                    player.sendSystemMessage(ModTexts.FLY_AGAIN);
                }
                this.damageTimeTicks--;
            } else if (this.wantToFlyAgain) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
                this.wantToFlyAgain = false;
            }
        }

        // Variables for flight and movement speed
        float movementSpeed = (float)this.getKnownMovement().horizontalDistance();

        float configuredFlightSpeed = player.getAbilities().getFlyingSpeed();
        float flightSpeedMultiplier = movementSpeed == 0 ? 0.25F : configuredFlightSpeed / 0.045F;
        if (flightSpeedMultiplier > 3.5F) {
            flightSpeedMultiplier = 3.5F;
        }

        // Exhaust player when flying
        if (options().flightExhaustion && hasElytra(player) && player.getAbilities().flying) {
            float exhaustion = flightSpeedMultiplier * movementSpeed;
            player.causeFoodExhaustion(exhaustion / 100);
        }

        // Stop further action, this prevents double jumping lol
        if (options().friendlyFlight.enabled() && this.damageTimeTicks > 0) {
            return;
        }

        // Elytra flight functionality (requires player to wear elytra to fly)
        if (options().elytraFlight) {
            ItemStack chestSlot = player.getItemBySlot(EquipmentSlot.CHEST);
            boolean validDurability = chestSlot.getDamageValue() != chestSlot.getMaxDamage() - 1;
            boolean isElytra = chestSlot.is(Items.ELYTRA);
            boolean elytraButNotValid = isElytra && !validDurability;
            if (!isElytra || elytraButNotValid) {
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                if (elytraButNotValid && !this.playedBroken) {
                    this.level().broadcastEntityEvent(this, (byte) 50);
                    this.playedBroken = true;
                }
            } else {
                if (((PlayerAbilitiesExtension)player).hasEverEnabledFlight()) {
                    player.getAbilities().mayfly = true;
                    if (isElytra && validDurability) {
                        this.playedBroken = false;
                    }
                }

                boolean isAirborneFlight = player.getAbilities().mayfly && !player.onGround() && !player.isFallFlying();
                if (isAirborneFlight && (player.getAbilities().flying && !player.isCrouching() || player.fallDistance == 0)) {
                    float durabilityCost = 0.04F * flightSpeedMultiplier * (movementSpeed == 0 ? 1 : movementSpeed);

                    this.elytraDurabilityProgress += durabilityCost;
                    int durabilityToDamage = (int)this.elytraDurabilityProgress;
                    if (durabilityToDamage > 0) {
                        this.elytraDurabilityProgress -= durabilityToDamage;
                        if (chestSlot.getDamageValue() != chestSlot.getMaxDamage() - 1) {
                            chestSlot.hurtAndBreak(durabilityToDamage, this, EquipmentSlot.CHEST);
                        }
                    }
                }
            }
            player.onUpdateAbilities();
        }
    }
}
