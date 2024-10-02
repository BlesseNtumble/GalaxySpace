/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.Loader
 *  net.minecraft.launchwrapper.LaunchClassLoader
 */
package galaxyspace.core.hooklib.minecraft;

import cpw.mods.fml.common.Loader;
import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.minecraft.MinecraftClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class SecondaryTransformerHook {
    @Hook
    public static void injectData(Loader loader, Object ... data) {
        ClassLoader classLoader = SecondaryTransformerHook.class.getClassLoader();
        if (classLoader instanceof LaunchClassLoader) {
            ((LaunchClassLoader)classLoader).registerTransformer(MinecraftClassTransformer.class.getName());
        } else {
            System.out.println("HookLib was not loaded by LaunchClassLoader. Hooks will not be injected.");
        }
    }
}

