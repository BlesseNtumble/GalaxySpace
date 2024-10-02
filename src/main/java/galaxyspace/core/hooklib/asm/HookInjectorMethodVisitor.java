/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Label
 *  org.objectweb.asm.MethodVisitor
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.commons.AdviceAdapter
 */
package galaxyspace.core.hooklib.asm;

import galaxyspace.core.hooklib.asm.AsmHook;
import galaxyspace.core.hooklib.asm.HookInjectorClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public abstract class HookInjectorMethodVisitor
extends AdviceAdapter {
    protected final AsmHook hook;
    protected final HookInjectorClassVisitor cv;
    public final String methodName;
    public final Type methodType;
    public final boolean isStatic;

    protected HookInjectorMethodVisitor(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv) {
        super(327680, mv, access, name, desc);
        this.hook = hook;
        this.cv = cv;
        this.isStatic = (access & 8) != 0;
        this.methodName = name;
        this.methodType = Type.getMethodType((String)desc);
    }

    protected final void visitHook() {
        if (!this.cv.visitingHook) {
            this.cv.visitingHook = true;
            this.hook.inject(this);
            this.cv.visitingHook = false;
        }
    }

    MethodVisitor getBasicVisitor() {
        return this.mv;
    }

    public static class LineNumber
    extends HookInjectorMethodVisitor {
        private int lineNumber;

        public LineNumber(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv, int lineNumber) {
            super(mv, access, name, desc, hook, cv);
            this.lineNumber = lineNumber;
        }

        public void visitLineNumber(int line, Label start) {
            super.visitLineNumber(line, start);
            if (this.lineNumber == line) {
                this.visitHook();
            }
        }
    }

    public static class MethodExit
    extends HookInjectorMethodVisitor {
        public MethodExit(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv) {
            super(mv, access, name, desc, hook, cv);
        }

        protected void onMethodExit(int opcode) {
            if (opcode != 191) {
                this.visitHook();
            }
        }
    }

    public static class MethodEnter
    extends HookInjectorMethodVisitor {
        public MethodEnter(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv) {
            super(mv, access, name, desc, hook, cv);
        }

        protected void onMethodEnter() {
            this.visitHook();
        }
    }
}

