package galaxyspace.core.hooklib.minecraft;

import java.io.IOException;
import java.lang.reflect.Method;

import galaxyspace.core.hooklib.asm.ClassMetadataReader;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

/**
 * Еще больше костылей вдобавок к ClassMetadataReader для работы с майновской обфускацией.
 */
public class DeobfuscationMetadataReader extends ClassMetadataReader {

    private static Method runTransformers;

    static {
        try {
            runTransformers = LaunchClassLoader.class.getDeclaredMethod("runTransformers",
                    String.class, String.class, byte[].class);
            runTransformers.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getClassData(String className) throws IOException {
        byte[] bytes = super.getClassData(unmap(className.replace('.', '/')));
        return deobfuscateClass(className, bytes);
    }

    @Override
    protected boolean checkSameMethod(String sourceName, String sourceDesc, String targetName, String targetDesc) {
        return checkSameMethod(sourceName, targetName) && sourceDesc.equals(targetDesc);
    }

    // Фордж и прочее могут своими патчами добавлять методы, которые нужно уметь оверрайдить хуками.
    // Для этого приходится применять трансформеры во время поиска супер-методов
    // этот метод должен вызываться только во время загрузки сабклассов проверяемого класса,
    // так что все должно быть норм
    @Override
    protected MethodReference getMethodReferenceASM(String type, String methodName, String desc) throws IOException {
        FindMethodClassVisitor cv = new FindMethodClassVisitor(methodName, desc);
        byte[] bytes = getTransformedBytes(type);
        acceptVisitor(bytes, cv);
        return cv.found ? new MethodReference(type, cv.targetName, cv.targetDesc) : null;
    }

    static byte[] deobfuscateClass(String className, byte[] bytes) {
        if (HookLoader.getDeobfuscationTransformer() != null) {
            bytes = HookLoader.getDeobfuscationTransformer().transform(className, className, bytes);
        }
        return bytes;
    }

    private static byte[] getTransformedBytes(String type) throws IOException {
        String obfName = unmap(type);
        byte[] bytes = Launch.classLoader.getClassBytes(obfName);
        if (bytes == null) {
            throw new RuntimeException("Bytes for " + obfName + " not found");
        }
        try {
            bytes = (byte[]) runTransformers.invoke(Launch.classLoader, obfName, type, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // возвращает из необфусцированного названия типа обфусцированное
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

}
