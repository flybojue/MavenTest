package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;



/**
 * 日期相关值
 * 
 * @author renjy
 */
public class ReadProperties {

	// 根据key来读取property配置文件里的value值
	public static String getprop(String filename, String propname) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream("./target/classes/properties/"+filename+".properties"));
		 String str = props.getProperty(propname);
		return str;
		
	}

}