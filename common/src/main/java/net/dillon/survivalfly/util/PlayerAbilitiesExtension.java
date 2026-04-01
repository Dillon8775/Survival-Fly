package net.dillon.survivalfly.util;

public interface PlayerAbilitiesExtension {
    void setEverEnabledFlight(boolean value);
    void setWantToFlyAgain(boolean value);
    void setDamageTicks(int value);
    boolean hasEverEnabledFlight();
    boolean flyingAllowed();
}