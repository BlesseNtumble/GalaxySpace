/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper
 *  net.minecraft.launchwrapper.Launch
 *  net.minecraft.launchwrapper.LaunchClassLoader
 *  org.objectweb.asm.ClassVisitor
 */
package galaxyspace.core.hooklib.minecraft;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import galaxyspace.core.hooklib.asm.ClassMetadataReader;
import galaxyspace.core.hooklib.minecraft.HookLibPlugin;
import galaxyspace.core.hooklib.minecraft.HookLoader;
import galaxyspace.core.hooklib.minecraft.MinecraftClassTransformer;
import java.io.IOException;
import java.lang.reflect.Method;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.ClassVisitor;

public class DeobfuscationMetadataReader
extends ClassMetadataReader {
    private static Method runTransformers;

    @Override
    public byte[] getClassData(String className) throws IOException {
        byte[] bytes = super.getClassData(DeobfuscationMetadataReader.unmap(className.replace('.', '/')));
        return DeobfuscationMetadataReader.deobfuscateClass(className, bytes);
    }

    @Override
    protected boolean checkSameMethod(String sourceName, String sourceDesc, String targetName, String targetDesc) {
        return DeobfuscationMetadataReader.checkSameMethod(sourceName, targetName) && sourceDesc.equals(targetDesc);
    }

    @Override
    protected ClassMetadataReader.MethodReference getMethodReferenceASM(String type, String methodName, String desc) throws IOException {
        ClassMetadataReader.FindMethodClassVisitor cv = new ClassMetadataReader.FindMethodClassVisitor(methodName, desc);
        byte[] bytes = DeobfuscationMetadataReader.getTransformedBytes(type);
        this.acceptVisitor(bytes, (ClassVisitor)cv);
        return cv.found ? new ClassMetadataReader.MethodReference(type, cv.targetName, cv.targetDesc) : null;
    }

    static byte[] deobfuscateClass(String className, byte[] bytes) {
        if (HookLoader.deobfuscationTransformer != null) {
            bytes = HookLoader.deobfuscationTransformer.transform(className, className, bytes);
        }
        return bytes;
    }

    private static byte[] getTransformedBytes(String type) throws IOException {
        String obfName = DeobfuscationMetadataReader.unmap(type);
        byte[] bytes = Launch.classLoader.getClassBytes(obfName);
        if (bytes == null) {
            throw new RuntimeException("Bytes for " + obfName + " not found");
        }
        try {
            bytes = (byte[])runTransformers.invoke(Launch.classLoader, obfName, type, bytes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static String unmap(String type) {
        if (HookLibPlugin.getObfuscated()) {
            return FMLDeobfuscatingRemapper.INSTANCE.unmap(type);
        }
        return type;
    }

    private static boolean checkSameMethod(String srgName, String mcpName) {
        if (HookLibPlugin.getObfuscated() && MinecraftClassTransformer.instance != null) {
            int methodId = MinecraftClassTransformer.getMethodId(srgName);
            String remappedName = MinecraftClassTransformer.instance.getMethodNames().get(methodId);
            if (remappedName != null && remappedName.equals(mcpName)) {
                return true;
            }
        }
        return srgName.equals(mcpName);
    }

    static {
        try {
            runTransformers = LaunchClassLoader.class.getDeclaredMethod("runTransformers", String.class, String.class, byte[].class);
            runTransformers.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

