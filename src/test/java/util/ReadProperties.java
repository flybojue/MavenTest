package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

    /**
     * 根据文件名称、属性名获取相应属性值
     *
     * @throws IOException
     * @throws FileNotFoundException
     */

    public static String getProp(String folder, String fileName, String propName) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("./test-classes/" + folder + "/" + fileName + ".properties"));
        String str = props.getProperty(propName);
        return str;
    }

}