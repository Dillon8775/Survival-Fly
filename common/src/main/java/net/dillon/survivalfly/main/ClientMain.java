package net.dillon.survivalfly.main;

import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.survivalfly.platform.MultiLoader;

/**
 * The client entrypoint for Survival Fly.
 */
public class ClientMain {

    public static void cInitialize(BalmRegistrars balmRegistrars) {
        if (!MultiLoader.getPlatform().isYaclLoaded()) {
            throw new RuntimeException("YetAnotherConfigLib is required use Quality of Queso! Please install from Modrinth or CurseForge.");
        }
    }
}