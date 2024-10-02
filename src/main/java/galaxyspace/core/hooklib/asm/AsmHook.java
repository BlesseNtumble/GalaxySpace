/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Label
 *  org.objectweb.asm.MethodVisitor
 *  org.objectweb.asm.Type
 */
package galaxyspace.core.hooklib.asm;

import galaxyspace.core.hooklib.asm.ClassMetadataReader;
import galaxyspace.core.hooklib.asm.HookInjectorClassVisitor;
import galaxyspace.core.hooklib.asm.HookInjectorFactory;
import galaxyspace.core.hooklib.asm.HookInjectorMethodVisitor;
import galaxyspace.core.hooklib.asm.HookPriority;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import galaxyspace.core.hooklib.asm.ReturnValue;
import galaxyspace.core.hooklib.asm.TypeHelper;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class AsmHook
implements Cloneable,
Comparable<AsmHook> {
    private String targetClassName;
    private String targetMethodName;
    private List<Type> targetMethodParameters = new ArrayList<Type>(2);
    private Type targetMethodReturnType;
    private String hooksClassName;
    private String hookMethodName;
    private List<Integer> transmittableVariableIds = new ArrayList<Integer>(2);
    private List<Type> hookMethodParameters = new ArrayList<Type>(2);
    private Type hookMethodReturnType = Type.VOID_TYPE;
    private boolean hasReturnValueParameter;
    private ReturnCondition returnCondition = ReturnCondition.NEVER;
    private ReturnValue returnValue = ReturnValue.VOID;
    private Object primitiveConstant;
    private HookInjectorFactory injectorFactory = ON_ENTER_FACTORY;
    private HookPriority priority = HookPriority.NORMAL;
    public static final HookInjectorFactory ON_ENTER_FACTORY = HookInjectorFactory.MethodEnter.INSTANCE;
    public static final HookInjectorFactory ON_EXIT_FACTORY = HookInjectorFactory.MethodExit.INSTANCE;
    private String targetMethodDescription;
    private String hookMethodDescription;
    private String returnMethodName;
    private String returnMethodDescription;
    private boolean createMethod;
    private boolean isMandatory;

    protected String getTargetClassName() {
        return this.targetClassName;
    }

    private String getTargetClassInternalName() {
        return this.targetClassName.replace('.', '/');
    }

    private String getHookClassInternalName() {
        return this.hooksClassName.replace('.', '/');
    }

    protected boolean isTargetMethod(String name, String desc) {
        return (this.targetMethodReturnType == null && desc.startsWith(this.targetMethodDescription) || desc.equals(this.targetMethodDescription)) && name.equals(this.targetMethodName);
    }

    protected boolean getCreateMethod() {
        return this.createMethod;
    }

    protected boolean isMandatory() {
        return this.isMandatory;
    }

    protected HookInjectorFactory getInjectorFactory() {
        return this.injectorFactory;
    }

    private boolean hasHookMethod() {
        return this.hookMethodName != null && this.hooksClassName != null;
    }

    protected void createMethod(HookInjectorClassVisitor classVisitor) {
        HookInjectorMethodVisitor inj;
        ClassMetadataReader.MethodReference superMethod = classVisitor.transformer.classMetadataReader.findVirtualMethod(this.getTargetClassInternalName(), this.targetMethodName, this.targetMethodDescription);
        MethodVisitor mv = classVisitor.visitMethod(1, superMethod == null ? this.targetMethodName : superMethod.name, this.targetMethodDescription, null, null);
        if (mv instanceof HookInjectorMethodVisitor) {
            inj = (HookInjectorMethodVisitor)mv;
            inj.visitCode();
            inj.visitLabel(new Label());
            if (superMethod == null) {
                this.injectDefaultValue(inj, this.targetMethodReturnType);
            } else {
                this.injectSuperCall(inj, superMethod);
            }
        } else {
            throw new IllegalArgumentException("Hook injector not created");
        }
        this.injectReturn(inj, this.targetMethodReturnType);
        inj.visitLabel(new Label());
        inj.visitMaxs(0, 0);
        inj.visitEnd();
    }

    protected void inject(HookInjectorMethodVisitor inj) {
        Type targetMethodReturnType = inj.methodType.getReturnType();
        int returnLocalId = -1;
        if (this.hasReturnValueParameter) {
            returnLocalId = inj.newLocal(targetMethodReturnType);
            inj.visitVarInsn(targetMethodReturnType.getOpcode(54), returnLocalId);
        }
        int hookResultLocalId = -1;
        if (this.hasHookMethod()) {
            this.injectInvokeStatic(inj, returnLocalId, this.hookMethodName, this.hookMethodDescription);
            if (this.returnValue == ReturnValue.HOOK_RETURN_VALUE || this.returnCondition.requiresCondition) {
                hookResultLocalId = inj.newLocal(this.hookMethodReturnType);
                inj.visitVarInsn(this.hookMethodReturnType.getOpcode(54), hookResultLocalId);
            }
        }
        if (this.returnCondition != ReturnCondition.NEVER) {
            Label label = inj.newLabel();
            if (this.returnCondition != ReturnCondition.ALWAYS) {
                inj.visitVarInsn(this.hookMethodReturnType.getOpcode(21), hookResultLocalId);
                if (this.returnCondition == ReturnCondition.ON_TRUE) {
                    inj.visitJumpInsn(153, label);
                } else if (this.returnCondition == ReturnCondition.ON_NULL) {
                    inj.visitJumpInsn(199, label);
                } else if (this.returnCondition == ReturnCondition.ON_NOT_NULL) {
                    inj.visitJumpInsn(198, label);
                }
            }
            if (this.returnValue == ReturnValue.NULL) {
                inj.visitInsn(1);
            } else if (this.returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
                inj.visitLdcInsn(this.primitiveConstant);
            } else if (this.returnValue == ReturnValue.HOOK_RETURN_VALUE) {
                inj.visitVarInsn(this.hookMethodReturnType.getOpcode(21), hookResultLocalId);
            } else if (this.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                String returnMethodDescription = this.returnMethodDescription;
                if (returnMethodDescription.endsWith(")")) {
                    returnMethodDescription = returnMethodDescription + targetMethodReturnType.getDescriptor();
                }
                this.injectInvokeStatic(inj, returnLocalId, this.returnMethodName, returnMethodDescription);
            }
            this.injectReturn(inj, targetMethodReturnType);
            inj.visitLabel(label);
        }
        if (this.hasReturnValueParameter) {
            this.injectLoad(inj, targetMethodReturnType, returnLocalId);
        }
    }

    private void injectLoad(HookInjectorMethodVisitor inj, Type parameterType, int variableId) {
        int opcode = parameterType == Type.INT_TYPE || parameterType == Type.BYTE_TYPE || parameterType == Type.CHAR_TYPE || parameterType == Type.BOOLEAN_TYPE || parameterType == Type.SHORT_TYPE ? 21 : (parameterType == Type.LONG_TYPE ? 22 : (parameterType == Type.FLOAT_TYPE ? 23 : (parameterType == Type.DOUBLE_TYPE ? 24 : 25)));
        inj.visitVarInsn(opcode, variableId);
    }

    private void injectSuperCall(HookInjectorMethodVisitor inj, ClassMetadataReader.MethodReference method) {
        int variableId = 0;
        for (int i = 0; i <= this.targetMethodParameters.size(); ++i) {
            Type parameterType = i == 0 ? TypeHelper.getType(this.targetClassName) : this.targetMethodParameters.get(i - 1);
            this.injectLoad(inj, parameterType, variableId);
            if (parameterType.getSort() == 8 || parameterType.getSort() == 7) {
                variableId += 2;
                continue;
            }
            ++variableId;
        }
        inj.visitMethodInsn(183, method.owner, method.name, method.desc, false);
    }

    private void injectDefaultValue(HookInjectorMethodVisitor inj, Type targetMethodReturnType) {
        switch (targetMethodReturnType.getSort()) {
            case 0: {
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                inj.visitInsn(3);
                break;
            }
            case 6: {
                inj.visitInsn(11);
                break;
            }
            case 7: {
                inj.visitInsn(9);
                break;
            }
            case 8: {
                inj.visitInsn(14);
                break;
            }
            default: {
                inj.visitInsn(1);
            }
        }
    }

    private void injectReturn(HookInjectorMethodVisitor inj, Type targetMethodReturnType) {
        if (targetMethodReturnType == Type.INT_TYPE || targetMethodReturnType == Type.SHORT_TYPE || targetMethodReturnType == Type.BOOLEAN_TYPE || targetMethodReturnType == Type.BYTE_TYPE || targetMethodReturnType == Type.CHAR_TYPE) {
            inj.visitInsn(172);
        } else if (targetMethodReturnType == Type.LONG_TYPE) {
            inj.visitInsn(173);
        } else if (targetMethodReturnType == Type.FLOAT_TYPE) {
            inj.visitInsn(174);
        } else if (targetMethodReturnType == Type.DOUBLE_TYPE) {
            inj.visitInsn(175);
        } else if (targetMethodReturnType == Type.VOID_TYPE) {
            inj.visitInsn(177);
        } else {
            inj.visitInsn(176);
        }
    }

    private void injectInvokeStatic(HookInjectorMethodVisitor inj, int returnLocalId, String name, String desc) {
        for (int i = 0; i < this.hookMethodParameters.size(); ++i) {
            Type parameterType = this.hookMethodParameters.get(i);
            int variableId = this.transmittableVariableIds.get(i);
            if (inj.isStatic) {
                if (variableId == 0) {
                    inj.visitInsn(1);
                    continue;
                }
                if (variableId > 0) {
                    --variableId;
                }
            }
            if (variableId == -1) {
                variableId = returnLocalId;
            }
            this.injectLoad(inj, parameterType, variableId);
        }
        inj.visitMethodInsn(184, this.getHookClassInternalName(), name, desc, false);
    }

    public String getPatchedMethodName() {
        return this.targetClassName + '#' + this.targetMethodName + this.targetMethodDescription;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AsmHook: ");
        sb.append(this.targetClassName).append('#').append(this.targetMethodName);
        sb.append(this.targetMethodDescription);
        sb.append(" -> ");
        sb.append(this.hooksClassName).append('#').append(this.hookMethodName);
        sb.append(this.hookMethodDescription);
        sb.append(", ReturnCondition=" + (Object)((Object)this.returnCondition));
        sb.append(", ReturnValue=" + (Object)((Object)this.returnValue));
        if (this.returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
            sb.append(", Constant=" + this.primitiveConstant);
        }
        sb.append(", InjectorFactory: " + this.injectorFactory.getClass().getName());
        sb.append(", CreateMethod = " + this.createMethod);
        return sb.toString();
    }

    @Override
    public int compareTo(AsmHook o) {
        if (this.injectorFactory.isPriorityInverted && o.injectorFactory.isPriorityInverted) {
            return this.priority.ordinal() > o.priority.ordinal() ? -1 : 1;
        }
        if (!this.injectorFactory.isPriorityInverted && !o.injectorFactory.isPriorityInverted) {
            return this.priority.ordinal() > o.priority.ordinal() ? 1 : -1;
        }
        return this.injectorFactory.isPriorityInverted ? 1 : -1;
    }

    public static Builder newBuilder() {
        AsmHook asmHook = new AsmHook();
        asmHook.getClass();
        return asmHook.new Builder();
    }

    public class Builder
    extends AsmHook {
        private Builder() {
        }

        public Builder setTargetClass(String className) {
            AsmHook.this.targetClassName = className;
            return this;
        }

        public Builder setTargetMethod(String methodName) {
            AsmHook.this.targetMethodName = methodName;
            return this;
        }

        public Builder addTargetMethodParameters(Type ... parameterTypes) {
            for (Type type : parameterTypes) {
                AsmHook.this.targetMethodParameters.add(type);
            }
            return this;
        }

        public Builder addTargetMethodParameters(String ... parameterTypeNames) {
            Type[] types = new Type[parameterTypeNames.length];
            for (int i = 0; i < parameterTypeNames.length; ++i) {
                types[i] = TypeHelper.getType(parameterTypeNames[i]);
            }
            return this.addTargetMethodParameters(types);
        }

        public Builder setTargetMethodReturnType(Type returnType) {
            AsmHook.this.targetMethodReturnType = returnType;
            return this;
        }

        public Builder setTargetMethodReturnType(String returnType) {
            return this.setTargetMethodReturnType(TypeHelper.getType(returnType));
        }

        public Builder setHookClass(String className) {
            AsmHook.this.hooksClassName = className;
            return this;
        }

        public Builder setHookMethod(String methodName) {
            AsmHook.this.hookMethodName = methodName;
            return this;
        }

        public Builder addHookMethodParameter(Type parameterType, int variableId) {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append parameter to its parameters list.");
            }
            AsmHook.this.hookMethodParameters.add(parameterType);
            AsmHook.this.transmittableVariableIds.add(variableId);
            return this;
        }

        public Builder addHookMethodParameter(String parameterTypeName, int variableId) {
            return this.addHookMethodParameter(TypeHelper.getType(parameterTypeName), variableId);
        }

        public Builder addThisToHookMethodParameters() {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append parameter to its parameters list.");
            }
            AsmHook.this.hookMethodParameters.add(TypeHelper.getType(AsmHook.this.targetClassName));
            AsmHook.this.transmittableVariableIds.add(0);
            return this;
        }

        public Builder addReturnValueToHookMethodParameters() {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append parameter to its parameters list.");
            }
            if (AsmHook.this.targetMethodReturnType == Type.VOID_TYPE) {
                throw new IllegalStateException("Target method's return type is void, it does not make sense to transmit its return value to hook method.");
            }
            AsmHook.this.hookMethodParameters.add(AsmHook.this.targetMethodReturnType);
            AsmHook.this.transmittableVariableIds.add(-1);
            AsmHook.this.hasReturnValueParameter = true;
            return this;
        }

        public Builder setReturnCondition(ReturnCondition condition) {
            Type returnType;
            if (condition.requiresCondition && AsmHook.this.hookMethodName == null) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return condition that depends on hook method.");
            }
            AsmHook.this.returnCondition = condition;
            switch (condition) {
                case NEVER: 
                case ALWAYS: {
                    returnType = Type.VOID_TYPE;
                    break;
                }
                case ON_TRUE: {
                    returnType = Type.BOOLEAN_TYPE;
                    break;
                }
                default: {
                    returnType = Type.getType(Object.class);
                }
            }
            AsmHook.this.hookMethodReturnType = returnType;
            return this;
        }

        public Builder setReturnValue(ReturnValue value) {
            if (AsmHook.this.returnCondition == ReturnCondition.NEVER) {
                throw new IllegalStateException("Current return condition is ReturnCondition.NEVER, so it does not make sense to specify the return value.");
            }
            Type returnType = AsmHook.this.targetMethodReturnType;
            if (value != ReturnValue.VOID && returnType == Type.VOID_TYPE) {
                throw new IllegalArgumentException("Target method return value is void, so it does not make sense to return anything else.");
            }
            if (value == ReturnValue.VOID && returnType != Type.VOID_TYPE) {
                throw new IllegalArgumentException("Target method return value is not void, so it is impossible to return VOID.");
            }
            if (value == ReturnValue.PRIMITIVE_CONSTANT && returnType != null && !this.isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is not a primitive, so it is impossible to return PRIVITIVE_CONSTANT.");
            }
            if (value == ReturnValue.NULL && returnType != null && this.isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is a primitive, so it is impossible to return NULL.");
            }
            if (value == ReturnValue.HOOK_RETURN_VALUE && !AsmHook.this.hasHookMethod()) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return value that depends on hook method.");
            }
            AsmHook.this.returnValue = value;
            if (value == ReturnValue.HOOK_RETURN_VALUE) {
                AsmHook.this.hookMethodReturnType = AsmHook.this.targetMethodReturnType;
            }
            return this;
        }

        public Type getHookMethodReturnType() {
            return AsmHook.this.hookMethodReturnType;
        }

        protected void setHookMethodReturnType(Type type) {
            AsmHook.this.hookMethodReturnType = type;
        }

        private boolean isPrimitive(Type type) {
            return type.getSort() > 0 && type.getSort() < 9;
        }

        public Builder setPrimitiveConstant(Object constant) {
            if (AsmHook.this.returnValue != ReturnValue.PRIMITIVE_CONSTANT) {
                throw new IllegalStateException("Return value is not PRIMITIVE_CONSTANT, so it does not make senceto specify that constant.");
            }
            Type returnType = AsmHook.this.targetMethodReturnType;
            if (returnType == Type.BOOLEAN_TYPE && !(constant instanceof Boolean) || returnType == Type.CHAR_TYPE && !(constant instanceof Character) || returnType == Type.BYTE_TYPE && !(constant instanceof Byte) || returnType == Type.SHORT_TYPE && !(constant instanceof Short) || returnType == Type.INT_TYPE && !(constant instanceof Integer) || returnType == Type.LONG_TYPE && !(constant instanceof Long) || returnType == Type.FLOAT_TYPE && !(constant instanceof Float) || returnType == Type.DOUBLE_TYPE && !(constant instanceof Double)) {
                throw new IllegalArgumentException("Given object class does not math target method return type.");
            }
            AsmHook.this.primitiveConstant = constant;
            return this;
        }

        public Builder setReturnMethod(String methodName) {
            if (AsmHook.this.returnValue != ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                throw new IllegalStateException("Return value is not ANOTHER_METHOD_RETURN_VALUE, so it does not make sence to specify that method.");
            }
            AsmHook.this.returnMethodName = methodName;
            return this;
        }

        public Builder setInjectorFactory(HookInjectorFactory factory) {
            AsmHook.this.injectorFactory = factory;
            return this;
        }

        public Builder setPriority(HookPriority priority) {
            AsmHook.this.priority = priority;
            return this;
        }

        public Builder setCreateMethod(boolean createMethod) {
            AsmHook.this.createMethod = createMethod;
            return this;
        }

        public Builder setMandatory(boolean isMandatory) {
            AsmHook.this.isMandatory = isMandatory;
            return this;
        }

        private String getMethodDesc(Type returnType, List<Type> paramTypes) {
            Type[] paramTypesArray = paramTypes.toArray(new Type[0]);
            if (returnType == null) {
                String voidDesc = Type.getMethodDescriptor((Type)Type.VOID_TYPE, (Type[])paramTypesArray);
                return voidDesc.substring(0, voidDesc.length() - 1);
            }
            return Type.getMethodDescriptor((Type)returnType, (Type[])paramTypesArray);
        }

        public AsmHook build() {
            AsmHook hook = AsmHook.this;
            if (hook.createMethod && hook.targetMethodReturnType == null) {
                hook.targetMethodReturnType = hook.hookMethodReturnType;
            }
            hook.targetMethodDescription = this.getMethodDesc(hook.targetMethodReturnType, hook.targetMethodParameters);
            if (hook.hasHookMethod()) {
                hook.hookMethodDescription = Type.getMethodDescriptor((Type)hook.hookMethodReturnType, (Type[])hook.hookMethodParameters.toArray(new Type[0]));
            }
            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                hook.returnMethodDescription = this.getMethodDesc(hook.targetMethodReturnType, hook.hookMethodParameters);
            }
            try {
                hook = (AsmHook)AsmHook.this.clone();
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                // empty catch block
            }
            if (hook.targetClassName == null) {
                throw new IllegalStateException("Target class name is not specified. Call setTargetClassName() before build().");
            }
            if (hook.targetMethodName == null) {
                throw new IllegalStateException("Target method name is not specified. Call setTargetMethodName() before build().");
            }
            if (hook.returnValue == ReturnValue.PRIMITIVE_CONSTANT && hook.primitiveConstant == null) {
                throw new IllegalStateException("Return value is PRIMITIVE_CONSTANT, but the constant is not specified. Call setReturnValue() before build().");
            }
            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE && hook.returnMethodName == null) {
                throw new IllegalStateException("Return value is ANOTHER_METHOD_RETURN_VALUE, but the method is not specified. Call setReturnMethod() before build().");
            }
            if (!(hook.injectorFactory instanceof HookInjectorFactory.MethodExit) && hook.hasReturnValueParameter) {
                throw new IllegalStateException("Can not pass return value to hook method because hook location is not return insn.");
            }
            return hook;
        }
    }
}

