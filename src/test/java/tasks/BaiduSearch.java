package tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import util.ReadProperties;

import java.io.IOException;

public class BaiduSearch {

    public static void search(WebDriver driver, String searchWords1, String searchWords2) throws InterruptedException, IOException {
        driver.findElement(By.xpath(ReadProperties.getProp("elements","baiduElements",
                "searchInput"))).clear();
        driver.findElement(By.xpath(ReadProperties.getProp("elements","baiduElements",
                "searchInput"))).sendKeys(searchWords1);
        driver.findElement(By.xpath(ReadProperties.getProp("elements","baiduElements",
                "searchButton"))).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath(ReadProperties.getProp("elements","baiduElements",
                "searchInput"))).clear();
        driver.findElement(By.xpath(ReadProperties.getProp("elements","baiduElements",
                "searchInput"))).sendKeys(searchWords2);
        driver.findElement(By.xpath(ReadProperties.getProp("elements","baiduElements",
                "searchButton"))).click();
        Thread.sleep(2000);

    }

}
