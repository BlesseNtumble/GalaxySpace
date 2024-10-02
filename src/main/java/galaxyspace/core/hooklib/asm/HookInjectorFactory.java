/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.MethodVisitor
 */
package galaxyspace.core.hooklib.asm;

import galaxyspace.core.hooklib.asm.AsmHook;
import galaxyspace.core.hooklib.asm.HookInjectorClassVisitor;
import galaxyspace.core.hooklib.asm.HookInjectorMethodVisitor;
import org.objectweb.asm.MethodVisitor;

public abstract class HookInjectorFactory {
    protected boolean isPriorityInverted = false;

    abstract HookInjectorMethodVisitor createHookInjector(MethodVisitor var1, int var2, String var3, String var4, AsmHook var5, HookInjectorClassVisitor var6);

    static class LineNumber
    extends HookInjectorFactory {
        private int lineNumber;

        public LineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        @Override
        public HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.LineNumber(mv, access, name, desc, hook, cv, this.lineNumber);
        }
    }

    static class MethodExit
    extends HookInjectorFactory {
        public static final MethodExit INSTANCE = new MethodExit();

        private MethodExit() {
            this.isPriorityInverted = true;
        }

        @Override
        public HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.MethodExit(mv, access, name, desc, hook, cv);
        }
    }

    static class MethodEnter
    extends HookInjectorFactory {
        public static final MethodEnter INSTANCE = new MethodEnter();

        private MethodEnter() {
        }

        @Override
        public HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc, AsmHook hook, HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.MethodEnter(mv, access, name, desc, hook, cv);
        }
    }
}

