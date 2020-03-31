package galaxyspace.core.hooklib.helper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * Генерирует из mcp-шного methods.csv словарь с названиями методов для хуклибы.
 * Файл methods.csv лежит в mcp/conf/
 *
 * Настоятельно рекомендую сгенерировать methods.bin самостоятельно для своей версии mcp, иначе могут быть
 * внезапные ошибки уровня "can not find target method of hook".
 */
public class DictionaryGenerator {

    public static void main(String[] args) throws Exception {
        List<String> lines = FileUtils.readLines(new File("methods.csv"));
        lines.remove(0);
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (String str : lines) {
            String[] splitted = str.split(",");
            int first = splitted[0].indexOf('_');
            int second = splitted[0].indexOf('_', first+1);
            int id = Integer.valueOf(splitted[0].substring(first+1, second));
            map.put(id, splitted[1]);
        }

        DataOutputStream out = new DataOutputStream(new FileOutputStream("methods.bin"));
        out.writeInt(map.size());

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            out.writeInt(entry.getKey());
            out.writeUTF(entry.getValue());
        }

        out.close();

    }
}
