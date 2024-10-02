/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassWriter
 */
package galaxyspace.core.hooklib.asm;

import galaxyspace.core.hooklib.asm.ClassMetadataReader;
import java.util.ArrayList;
import org.objectweb.asm.ClassWriter;

public class SafeClassWriter
extends ClassWriter {
    private final ClassMetadataReader classMetadataReader;

    public SafeClassWriter(ClassMetadataReader classMetadataReader, int flags) {
        super(flags);
        this.classMetadataReader = classMetadataReader;
    }

    protected String getCommonSuperClass(String type1, String type2) {
        int i;
        ArrayList<String> superClasses1 = this.classMetadataReader.getSuperClasses(type1);
        ArrayList<String> superClasses2 = this.classMetadataReader.getSuperClasses(type2);
        int size = Math.min(superClasses1.size(), superClasses2.size());
        for (i = 0; i < size && superClasses1.get(i).equals(superClasses2.get(i)); ++i) {
        }
        if (i == 0) {
            return "java/lang/Object";
        }
        return superClasses1.get(i - 1);
    }
}

