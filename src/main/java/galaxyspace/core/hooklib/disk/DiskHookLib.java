/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 */
package galaxyspace.core.hooklib.disk;

import galaxyspace.core.hooklib.asm.HookClassTransformer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class DiskHookLib {
    File untransformedDir = new File("untransformed");
    File transformedDir = new File("transformed");
    File hooksDir = new File("hooks");

    public static void main(String[] args) throws IOException {
        new DiskHookLib().process();
    }

    void process() throws IOException {
        HookClassTransformer transformer = new HookClassTransformer();
        for (File file : DiskHookLib.getFiles(".class", this.hooksDir)) {
            transformer.registerHookContainer(FileUtils.readFileToByteArray((File)file));
        }
        for (File file : DiskHookLib.getFiles(".class", this.untransformedDir)) {
            byte[] bytes = IOUtils.toByteArray((InputStream)new FileInputStream(file));
            String className = "";
            byte[] byArray = transformer.transform(className, bytes);
        }
    }

    private static List<File> getFiles(String postfix, File dir) throws IOException {
        ArrayList<File> files = new ArrayList<File>();
        File[] filesArray = dir.listFiles();
        if (filesArray != null) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    files.addAll(DiskHookLib.getFiles(postfix, file));
                    continue;
                }
                if (!file.getName().toLowerCase().endsWith(postfix)) continue;
                files.add(file);
            }
        }
        return files;
    }
}

