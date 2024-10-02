/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 */
package galaxyspace.core.hooklib.asm;

import java.io.InputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

public class ReadClassHelper {
    public static InputStream getClassData(String className) {
        String classResourceName = '/' + className.replace('.', '/') + ".class";
        return ReadClassHelper.class.getResourceAsStream(classResourceName);
    }

    public static void acceptVisitor(InputStream classData, ClassVisitor visitor) {
        try {
            ClassReader reader = new ClassReader(classData);
            reader.accept(visitor, 0);
            classData.close();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void acceptVisitor(String className, ClassVisitor visitor) {
        ReadClassHelper.acceptVisitor(ReadClassHelper.getClassData(className), visitor);
    }
}

