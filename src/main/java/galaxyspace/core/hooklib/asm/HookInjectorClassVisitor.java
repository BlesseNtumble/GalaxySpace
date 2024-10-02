/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.MethodVisitor
 */
package galaxyspace.core.hooklib.asm;

import galaxyspace.core.hooklib.asm.AsmHook;
import galaxyspace.core.hooklib.asm.HookClassTransformer;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class HookInjectorClassVisitor
extends ClassVisitor {
    List<AsmHook> hooks;
    List<AsmHook> injectedHooks = new ArrayList<AsmHook>(1);
    boolean visitingHook;
    HookClassTransformer transformer;
    String superName;

    public HookInjectorClassVisitor(HookClassTransformer transformer, ClassWriter cv, List<AsmHook> hooks) {
        super(327680, (ClassVisitor)cv);
        this.hooks = hooks;
        this.transformer = transformer;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.superName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        for (AsmHook hook : this.hooks) {
            if (!this.isTargetMethod(hook, name, desc) || this.injectedHooks.contains(hook)) continue;
            mv = hook.getInjectorFactory().createHookInjector((MethodVisitor)mv, access, name, desc, hook, this);
            this.injectedHooks.add(hook);
        }
        return mv;
    }

    public void visitEnd() {
        for (AsmHook hook : this.hooks) {
            if (!hook.getCreateMethod() || this.injectedHooks.contains(hook)) continue;
            hook.createMethod(this);
        }
        super.visitEnd();
    }

    protected boolean isTargetMethod(AsmHook hook, String name, String desc) {
        return hook.isTargetMethod(name, desc);
    }
}

