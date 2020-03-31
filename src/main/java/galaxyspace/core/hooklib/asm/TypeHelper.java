package galaxyspace.core.hooklib.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Класс, позволяющий создавать типы из разных входных данных.
 * Эти типы нужны для того, чтобы задавать параметры и возвращаемые значения методов.
 */
public class TypeHelper {

    private static final Map<String, Type> primitiveTypes = new HashMap<String, Type>(9);

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

    /**
     * Создает тип по названию класса или примитива.
     * Пример использования: getType("net.minecraft.world.World") - вернёт тип для World
     *
     * @param className необфусцированное название класса
     * @return соответствующий тип
     */
    public static Type getType(String className) {
        return getArrayType(className, 0);
    }

    /**
     * Создает тип для одномерного массива указанного класса или примитиа.
     * Пример использования: getArrayType("net.minecraft.world.World") - вернёт тип для World[]
     *
     * @param className необфусцированное название класса
     * @return соответствующий классу тип одномерного массива
     */
    public static Type getArrayType(String className) {
        return getArrayType(className, 1);
    }

    /**
     * Создает тип для n-мерного массива указанного класса или примитива.
     * Пример использования: getArrayType("net.minecraft.world.World", 2) - вернёт тип для World[][]
     *
     * @param className название класса
     * @return соответствующий классу тип n-мерного массива
     */
    public static Type getArrayType(String className, int arrayDimensions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayDimensions; i++) {
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
        return Type.getType(sb.toString());
    }

    static Object getStackMapFrameEntry(Type type) {
        if (type == Type.BOOLEAN_TYPE || type == Type.BYTE_TYPE || type == Type.SHORT_TYPE ||
                type == Type.CHAR_TYPE || type == Type.INT_TYPE) {
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
}
