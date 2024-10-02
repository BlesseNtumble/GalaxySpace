/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.asm.transformers.DeobfuscationTransformer
 *  cpw.mods.fml.relauncher.IFMLLoadingPlugin
 */
package galaxyspace.core.hooklib.minecraft;

import cpw.mods.fml.common.asm.transformers.DeobfuscationTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import galaxyspace.core.hooklib.asm.AsmHook;
import galaxyspace.core.hooklib.asm.ClassMetadataReader;
import galaxyspace.core.hooklib.asm.HookClassTransformer;
import galaxyspace.core.hooklib.minecraft.DeobfuscationMetadataReader;
import galaxyspace.core.hooklib.minecraft.HookLibPlugin;
import galaxyspace.core.hooklib.minecraft.MinecraftClassTransformer;
import galaxyspace.core.hooklib.minecraft.PrimaryClassTransformer;
import java.util.Map;

public abstract class HookLoader
implements IFMLLoadingPlugin {
    static DeobfuscationTransformer deobfuscationTransformer;
    private static ClassMetadataReader deobfuscationMetadataReader;

    public static HookClassTransformer getTransformer() {
        return PrimaryClassTransformer.instance.registeredSecondTransformer ? MinecraftClassTransformer.instance : PrimaryClassTransformer.instance;
    }

    public static void registerHook(AsmHook hook) {
        HookLoader.getTransformer().registerHook(hook);
    }

    public static void registerHookContainer(String className) {
        HookLoader.getTransformer().registerHookContainer(className);
    }

    public static ClassMetadataReader getDeobfuscationMetadataReader() {
        return deobfuscationMetadataReader;
    }

    public String[] getLibraryRequestClass() {
        return null;
    }

    public String getAccessTransformerClass() {
        return null;
    }

    public String[] getASMTransformerClass() {
        return null;
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
        this.registerHooks();
    }

    protected abstract void registerHooks();

    static {
        if (HookLibPlugin.getObfuscated()) {
            deobfuscationTransformer = new DeobfuscationTransformer();
        }
        deobfuscationMetadataReader = new DeobfuscationMetadataReader();
    }
}

