package net.dillon.survivalfly.platform;

import net.dillon.dillonlib.platform.Platforms;
import net.dillon.dillonlib.platform.info.ModReference;

public class ModReferences {
    public static final ModReference YACL = new ModReference("yet_another_config_lib_v3");
    public static final ModReference LUCKPERMS = new ModReference("luckperms");

    public static boolean isModLoaded(ModReference reference) {
        return Platforms.getDillonLibMixinPlatform().isModLoaded(reference);
    }
}