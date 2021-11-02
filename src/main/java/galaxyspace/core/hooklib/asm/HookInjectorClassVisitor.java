package galaxyspace.core.hooklib.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class HookInjectorClassVisitor extends ClassVisitor {

    List<AsmHook> hooks;
    List<AsmHook> injectedHooks = new ArrayList<AsmHook>(1);
    boolean visitingHook;
    HookClassTransformer transformer;

    String superName;

    public HookInjectorClassVisitor(HookClassTransformer transformer, ClassWriter cv, List<AsmHook> hooks) {
        super(Opcodes.ASM5, cv);
        this.hooks = hooks;
        this.transformer = transformer;
    }

    @Override public void visit(int version, int access, String name,
                                String signature, String superName, String[] interfaces) {
        this.superName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        for (AsmHook hook : hooks) {
            if (isTargetMethod(hook, name, desc) && !injectedHooks.contains(hook)) {
                // добавляет MethodVisitor в цепочку
                mv = hook.getInjectorFactory().createHookInjector(mv, access, name, desc, hook, this);
                injectedHooks.add(hook);
            }
        }
        return mv;
    }
    
    @Override
    public void visitEnd() {
        for (AsmHook hook : hooks) {
            if (hook.getCreateMethod() && !injectedHooks.contains(hook)) {
                hook.createMethod(this);
            }
        }
        super.visitEnd();
    }

    protected boolean isTargetMethod(AsmHook hook, String name, String desc) {
        return hook.isTargetMethod(name, desc);
    }
}
