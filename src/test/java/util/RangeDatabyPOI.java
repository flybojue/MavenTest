package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class RangeDatabyPOI {

    public static Object[][] poiRangeData(String filePath) throws IOException {

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;
        String extensionName = filePath.substring(filePath.indexOf("."));
        // 根据Excel的后缀名，在判断是旧版的xls还是新版的xlsx
        if (extensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (extensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            System.out.println("文件格式不正确");
        }

        // 默认读取第一个sheet页,可优化修改sheetName
        Sheet sheet = workbook.getSheetAt(0);

        // 获取全部的行数
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        List<Object[]> records = new ArrayList<Object[]>();
        // 默认第一行为title，数据不取；遍历每一行
        for (int i = 1; i < rowCount + 1; i++) {
            Row row = sheet.getRow(i);
            String fields[] = new String[row.getLastCellNum()];
            // 遍历每一行中的每一格
            for (int j = 0; j < row.getLastCellNum(); j++) {
                fields[j] = row.getCell(j).getStringCellValue();
            }
            records.add(fields);
        }

        // 将records转换成二维Object[][]
        Object[][] results = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }
        return results;
    }

}
