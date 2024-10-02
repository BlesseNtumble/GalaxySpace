/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper
 *  net.minecraft.launchwrapper.IClassTransformer
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.Type
 */
package galaxyspace.core.hooklib.minecraft;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import galaxyspace.core.hooklib.asm.AsmHook;
import galaxyspace.core.hooklib.asm.HookClassTransformer;
import galaxyspace.core.hooklib.asm.HookInjectorClassVisitor;
import galaxyspace.core.hooklib.minecraft.HookLibPlugin;
import galaxyspace.core.hooklib.minecraft.HookLoader;
import galaxyspace.core.hooklib.minecraft.SecondaryTransformerHook;
import java.util.HashMap;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

public class PrimaryClassTransformer
extends HookClassTransformer
implements IClassTransformer {
    static PrimaryClassTransformer instance = new PrimaryClassTransformer();
    boolean registeredSecondTransformer;

    public PrimaryClassTransformer() {
        this.classMetadataReader = HookLoader.getDeobfuscationMetadataReader();
        if (instance != null) {
            this.hooksMap.putAll(instance.getHooksMap());
            instance.getHooksMap().clear();
        } else {
            this.registerHookContainer(SecondaryTransformerHook.class.getName());
        }
        instance = this;
    }

    public byte[] transform(String oldName, String newName, byte[] bytecode) {
        return this.transform(newName, bytecode);
    }

    @Override
    protected HookInjectorClassVisitor createInjectorClassVisitor(ClassWriter cw, List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(this, cw, hooks){

            @Override
            protected boolean isTargetMethod(AsmHook hook, String name, String desc) {
                return super.isTargetMethod(hook, name, PrimaryClassTransformer.mapDesc(desc));
            }
        };
    }

    HashMap<String, List<AsmHook>> getHooksMap() {
        return this.hooksMap;
    }

    static String mapDesc(String desc) {
        if (!HookLibPlugin.getObfuscated()) {
            return desc;
        }
        Type methodType = Type.getMethodType((String)desc);
        Type mappedReturnType = PrimaryClassTransformer.map(methodType.getReturnType());
        Type[] argTypes = methodType.getArgumentTypes();
        Type[] mappedArgTypes = new Type[argTypes.length];
        for (int i = 0; i < mappedArgTypes.length; ++i) {
            mappedArgTypes[i] = PrimaryClassTransformer.map(argTypes[i]);
        }
        return Type.getMethodDescriptor((Type)mappedReturnType, (Type[])mappedArgTypes);
    }

    static Type map(Type type) {
        if (!HookLibPlugin.getObfuscated()) {
            return type;
        }
        if (type.getSort() < 9) {
            return type;
        }
        if (type.getSort() == 9) {
            boolean isPrimitiveArray;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < type.getDimensions(); ++i) {
                sb.append("[");
            }
            boolean bl = isPrimitiveArray = type.getSort() < 9;
            if (!isPrimitiveArray) {
                sb.append("L");
            }
            sb.append(PrimaryClassTransformer.map(type.getElementType()).getInternalName());
            if (!isPrimitiveArray) {
                sb.append(";");
            }
            return Type.getType((String)sb.toString());
        }
        if (type.getSort() == 10) {
            String unmappedName = FMLDeobfuscatingRemapper.INSTANCE.map(type.getInternalName());
            return Type.getType((String)("L" + unmappedName + ";"));
        }
        throw new IllegalArgumentException("Can not map method type!");
    }
}

