package galaxyspace.core.hooklib.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import galaxyspace.core.hooklib.asm.HookClassTransformer;


public class DiskHookLib {

    public static void main(String[] args) throws IOException {
        new DiskHookLib().process();
    }

    File untransformedDir = new File("untransformed");
    File transformedDir = new File("transformed");
    File hooksDir = new File("hooks");

    void process() throws IOException {
        HookClassTransformer transformer = new HookClassTransformer();
        for (File file : getFiles(".class", hooksDir)) {
            transformer.registerHookContainer(FileUtils.readFileToByteArray(file));
            // теперь file надо скопировать в transformedDir, сохранив путь
        }
        for (File file : getFiles(".class", untransformedDir)) {
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
            String className = ""; //нужно из пути получить название класса через точки вроде ru.lol.DatClass
            byte[] newBytes = transformer.transform(className, bytes);
            // надо закинуть файл, состоящий из newBytes в transformedDir, сохранив путь
        }
    }

    private static List<File> getFiles(String postfix, File dir) throws IOException {
        ArrayList<File> files = new ArrayList<File>();
        File[] filesArray = dir.listFiles();
        if (filesArray != null) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    files.addAll(getFiles(postfix, file));
                } else if (file.getName().toLowerCase().endsWith(postfix)) {
                    files.add(file);
                }
            }
        }
        return files;
    }


}
