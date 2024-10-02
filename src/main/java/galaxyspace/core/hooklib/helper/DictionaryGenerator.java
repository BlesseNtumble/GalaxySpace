/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package galaxyspace.core.hooklib.helper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class DictionaryGenerator {
    public static void main(String[] args) throws Exception {
        List<String> lines = FileUtils.readLines((File)new File("methods.csv"));
        lines.remove(0);
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (String str : lines) {
            String[] splitted = str.split(",");
            int first = splitted[0].indexOf(95);
            int second = splitted[0].indexOf(95, first + 1);
            int id = Integer.valueOf(splitted[0].substring(first + 1, second));
            map.put(id, splitted[1]);
        }
        DataOutputStream out = new DataOutputStream(new FileOutputStream("methods.bin"));
        out.writeInt(map.size());
        for (Map.Entry entry : map.entrySet()) {
            out.writeInt((Integer)entry.getKey());
            out.writeUTF((String)entry.getValue());
        }
        out.close();
    }
}

