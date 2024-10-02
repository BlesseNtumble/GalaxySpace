/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  org.objectweb.asm.ClassWriter
 */
package galaxyspace.core.hooklib.minecraft;

import galaxyspace.core.hooklib.asm.AsmHook;
import galaxyspace.core.hooklib.asm.HookClassTransformer;
import galaxyspace.core.hooklib.asm.HookInjectorClassVisitor;
import galaxyspace.core.hooklib.minecraft.HookLibPlugin;
import galaxyspace.core.hooklib.minecraft.HookLoader;
import galaxyspace.core.hooklib.minecraft.PrimaryClassTransformer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassWriter;

public class MinecraftClassTransformer
extends HookClassTransformer
implements IClassTransformer {
    static MinecraftClassTransformer instance;
    private Map<Integer, String> methodNames;
    private static List<IClassTransformer> postTransformers;

    public MinecraftClassTransformer() {
        instance = this;
        if (HookLibPlugin.getObfuscated()) {
            try {
                long timeStart = System.currentTimeMillis();
                this.methodNames = this.loadMethodNames();
                long time = System.currentTimeMillis() - timeStart;
                this.logger.debug("Methods dictionary loaded in " + time + " ms");
            }
            catch (IOException e) {
                this.logger.severe("Can not load obfuscated method names", e);
            }
        }
        this.classMetadataReader = HookLoader.getDeobfuscationMetadataReader();
        this.hooksMap.putAll(PrimaryClassTransformer.instance.getHooksMap());
        PrimaryClassTransformer.instance.getHooksMap().clear();
        PrimaryClassTransformer.instance.registeredSecondTransformer = true;
    }

    private HashMap<Integer, String> loadMethodNames() throws IOException {
        InputStream resourceStream = this.getClass().getResourceAsStream("/methods.bin");
        if (resourceStream == null) {
            throw new IOException("Methods dictionary not found");
        }
        DataInputStream input = new DataInputStream(new BufferedInputStream(resourceStream));
        int numMethods = input.readInt();
        HashMap<Integer, String> map = new HashMap<Integer, String>(numMethods);
        for (int i = 0; i < numMethods; ++i) {
            map.put(input.readInt(), input.readUTF());
        }
        input.close();
        return map;
    }

    public byte[] transform(String oldName, String newName, byte[] bytecode) {
        bytecode = this.transform(newName, bytecode);
        for (int i = 0; i < postTransformers.size(); ++i) {
            bytecode = postTransformers.get(i).transform(oldName, newName, bytecode);
        }
        return bytecode;
    }

    @Override
    protected HookInjectorClassVisitor createInjectorClassVisitor(ClassWriter cw, List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(this, cw, hooks){

            @Override
            protected boolean isTargetMethod(AsmHook hook, String name, String desc) {
                String mcpName;
                if (HookLibPlugin.getObfuscated() && (mcpName = (String)MinecraftClassTransformer.this.methodNames.get(MinecraftClassTransformer.getMethodId(name))) != null && super.isTargetMethod(hook, mcpName, desc)) {
                    return true;
                }
                return super.isTargetMethod(hook, name, desc);
            }
        };
    }

    public Map<Integer, String> getMethodNames() {
        return this.methodNames;
    }

    public static int getMethodId(String srgName) {
        if (srgName.startsWith("func_")) {
            int first = srgName.indexOf(95);
            int second = srgName.indexOf(95, first + 1);
            return Integer.valueOf(srgName.substring(first + 1, second));
        }
        return -1;
    }

    public static void registerPostTransformer(IClassTransformer transformer) {
        postTransformers.add(transformer);
    }

    static {
        postTransformers = new ArrayList<IClassTransformer>();
    }
}

