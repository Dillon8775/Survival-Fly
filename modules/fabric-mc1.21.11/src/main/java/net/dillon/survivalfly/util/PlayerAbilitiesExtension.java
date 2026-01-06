package net.dillon.survivalfly.util;

/**
 * See {@link net.dillon.survivalfly.mixin.PlayerAbilitiesMixin}.
 */
public interface PlayerAbilitiesExtension {
    void setEverEnabledFlight(boolean value);
    boolean hasEverEnabledFlight();
}