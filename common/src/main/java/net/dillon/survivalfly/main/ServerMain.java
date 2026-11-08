package net.dillon.survivalfly.main;

import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillEnv;

/**
 * The server entrypoint for Survival Fly.
 */
@Dill(env = DillEnv.DEDICATED_SERVER)
public class ServerMain {

    public static void sInitialize(BalmRegistrars balmRegistrars) {
    }
}