/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Opcodes
 *  org.objectweb.asm.Type
 */
package galaxyspace.core.hooklib.asm;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class TypeHelper {
    private static final Map<String, Type> primitiveTypes = new HashMap<String, Type>(9);

    public static Type getType(String className) {
        return TypeHelper.getArrayType(className, 0);
    }

    public static Type getArrayType(String className) {
        return TypeHelper.getArrayType(className, 1);
    }

    public static Type getArrayType(String className, int arrayDimensions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayDimensions; ++i) {
            sb.append("[");
        }
        Type primitive = primitiveTypes.get(className);
        if (primitive == null) {
            sb.append("L");
            sb.append(className.replace(".", "/"));
            sb.append(";");
        } else {
            sb.append(primitive.getDescriptor());
        }
        return Type.getType((String)sb.toString());
    }

    static Object getStackMapFrameEntry(Type type) {
        if (type == Type.BOOLEAN_TYPE || type == Type.BYTE_TYPE || type == Type.SHORT_TYPE || type == Type.CHAR_TYPE || type == Type.INT_TYPE) {
            return Opcodes.INTEGER;
        }
        if (type == Type.FLOAT_TYPE) {
            return Opcodes.FLOAT;
        }
        if (type == Type.DOUBLE_TYPE) {
            return Opcodes.DOUBLE;
        }
        if (type == Type.LONG_TYPE) {
            return Opcodes.LONG;
        }
        return type.getInternalName();
    }

    static {
        primitiveTypes.put("void", Type.VOID_TYPE);
        primitiveTypes.put("boolean", Type.BOOLEAN_TYPE);
        primitiveTypes.put("byte", Type.BYTE_TYPE);
        primitiveTypes.put("short", Type.SHORT_TYPE);
        primitiveTypes.put("char", Type.CHAR_TYPE);
        primitiveTypes.put("int", Type.INT_TYPE);
        primitiveTypes.put("float", Type.FLOAT_TYPE);
        primitiveTypes.put("long", Type.LONG_TYPE);
        primitiveTypes.put("double", Type.DOUBLE_TYPE);
    }
}

