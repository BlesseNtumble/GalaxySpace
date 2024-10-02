/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.Label
 *  org.objectweb.asm.MethodVisitor
 *  org.objectweb.asm.Type
 */
package galaxyspace.core.hooklib.asm;

import galaxyspace.core.hooklib.asm.ClassMetadataReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class VariableIdHelper {
    private static ClassMetadataReader classMetadataReader = new ClassMetadataReader();

    public static List<String> listLocalVariables(byte[] classData, final String methodName, Type ... argTypes) {
        final ArrayList<String> localVariables = new ArrayList<String>();
        String methodDesc = Type.getMethodDescriptor((Type)Type.VOID_TYPE, (Type[])argTypes);
        final String methodDescWithoutReturnType = methodDesc.substring(0, methodDesc.length() - 1);
        ClassVisitor cv = new ClassVisitor(327680){

            public MethodVisitor visitMethod(final int acc, String name, String desc, String signature, String[] exceptions) {
                if (methodName.equals(name) && desc.startsWith(methodDescWithoutReturnType)) {
                    return new MethodVisitor(327680){

                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            String typeName = Type.getType((String)desc).getClassName();
                            int fixedIndex = index + ((acc & 8) != 0 ? 1 : 0);
                            localVariables.add(fixedIndex + ": " + typeName + " " + name);
                        }
                    };
                }
                return null;
            }
        };
        classMetadataReader.acceptVisitor(classData, cv);
        return localVariables;
    }

    public static List<String> listLocalVariables(String className, String methodName, Type ... argTypes) throws IOException {
        return VariableIdHelper.listLocalVariables(classMetadataReader.getClassData(className), methodName, argTypes);
    }

    public static void printLocalVariables(byte[] classData, String methodName, Type ... argTypes) {
        List<String> locals = VariableIdHelper.listLocalVariables(classData, methodName, argTypes);
        for (String str : locals) {
            System.out.println(str);
        }
    }

    public static void printLocalVariables(String className, String methodName, Type ... argTypes) throws IOException {
        VariableIdHelper.printLocalVariables(classMetadataReader.getClassData(className), methodName, argTypes);
    }
}

