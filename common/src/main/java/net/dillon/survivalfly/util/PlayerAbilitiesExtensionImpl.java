package net.dillon.survivalfly.util;

public interface PlayerAbilitiesExtensionImpl {
    void setEverEnabledFlight(boolean value);
    void setWantToFlyAgain(boolean value);
    void setDamageTicks(int value);
    int getDamageTicks();
    boolean hasEverEnabledFlight();
    boolean flyingAllowed();
}