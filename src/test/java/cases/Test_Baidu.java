package cases;

import data.TestSql;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tasks.BaiduSearch;
import util.Browser;
import util.RangeDatabyPOI;
import util.ReadProperties;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Test_Baidu {
    private static Logger logger = Logger.getLogger(Test_Baidu.class);

    WebDriver driver;
    String web;

    // @BeforeTest也可专门作为数据初始化
    @BeforeTest
    @Parameters({"url"})
    public void initBrowser(String url) {
        logger.info("打开的网址是：" + url);
        web = url;
        driver = Browser.openBrowser(driver, web);
    }

    @DataProvider(name = "data_excel")
    public Object[][] getData() throws IOException {
        String filepath = "d:/ExcelData_BaiduSearch.xlsx";
        Object[][] array = RangeDatabyPOI.poiRangeData(filepath);
        return array;
    }

    // 读取的Excel内的列数需与参数的个数一致
    @Test(dataProvider = "data_excel")
    public void loginBrowser(String searchWords1, String searchWords2) throws InterruptedException, IOException {
        BaiduSearch.search(driver, searchWords1, searchWords2);
    }

    // dataProvider可不用写在当前class里，可存在data文件夹中；然后通过dataProvider关联具体方法，dataProviderClass来关联class；
//    @Test(dataProvider = "selectSQL", dataProviderClass = TestSql.class)
//    public void testSQL(String nameFromSQL, String dateFromSQL, String remarksFromSQL) {
//        System.out.println("1: " + nameFromSQL);
//        System.out.println("2: " + dateFromSQL);
//        System.out.println("3: " + remarksFromSQL);
//    }

    @AfterTest
    public void quitBrowser() {
        Browser.closeBrowser(driver);
    }

}
