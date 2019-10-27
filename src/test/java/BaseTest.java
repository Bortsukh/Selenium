import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class BaseTest {

    private WebDriver driver;

    private BaseSite baseSite;

    @BeforeTest
    public void openSite() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //open test site
        driver.get("https://yandex.ru");
        driver.manage().window().maximize();
        baseSite = new BaseSite(driver);
        //catch exception
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(alwaysRun=true)
    public void findMobile() {
        //open Market
        baseSite.openMarket();
        //open electronic catalogue
        baseSite.openListofKinds();
        baseSite.openElectronicCatalogue();
        //choose samrtphone
        driver.findElement(By.xpath("//ul[contains(@class, '_1Y6X2G3jjK')]//a[contains(text(),'Смартфоны')]")).click();
        //choose from 20000 rub
        baseSite.setPriceFrom("20000");
        //choose Appel, Samsung
        driver.findElement(By.xpath("//span[contains(text(),'Apple')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Samsung')]")).click();
        //check qty more than 48
        List<WebElement> list = driver.findElements(By.className("n-snippet-cell2"));
        assertThat(12, lessThanOrEqualTo(list.size()));

        //copy name of first component
        driver.navigate().refresh();

        String mobileName = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-list')]//div[contains(@class,'n-snippet-cell2__title')][1]")).getText();
        driver.findElement(By.id("header-search")).sendKeys(mobileName);
        //click search
        driver.findElement(By.className("search2__button")).click();

        //check name
        String mobileNameForCheck = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-list')]//div[contains(@class,'n-snippet-cell2__title')][1]")).getText();
        assertThat(mobileName, equalTo(mobileNameForCheck));
    }

    @Test(alwaysRun = true)
    public void findHeadset(){
        //open Market
        baseSite.openMarket();
        //open electronic catalogue
        baseSite.openListofKinds();
        baseSite.openElectronicCatalogue();
        //choose headset
        driver.findElement(By.xpath("//ul[contains(@class, '_1Y6X2G3jjK')]//a[contains(text(),'Наушники и Bluetooth-гарнитуры')]")).click();
        //choose from 5000 rub
        baseSite.setPriceFrom("5000");
        //choose Beats
        driver.findElement(By.xpath("//span[contains(text(),'Beats')]")).click();
        //check qty more than 48
        List<WebElement> list = driver.findElements(By.className("n-snippet-cell2"));
        assertThat(12, lessThanOrEqualTo(list.size()));
        //copy name of first component
        driver.navigate().refresh();
        String headsetName = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-list')]//div[contains(@class,'n-snippet-cell2__title')][1]")).getText();
        driver.findElement(By.id("header-search")).sendKeys(headsetName);
        //click search
        driver.findElement(By.className("search2__button")).click();
        //check name
        String headsetNameForCheck = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-list')]//div[contains(@class,'n-snippet-cell2__title')][1]")).getText();
        assertThat(headsetName, equalTo(headsetNameForCheck));
    }

    @Test(alwaysRun = true)
    public void sortForSmartphone(){
        //open Market
        baseSite.openMarket();
        //open electronic catalogue
        baseSite.openListofKinds();
        baseSite.openElectronicCatalogue();
        //choose headset
        driver.findElement(By.xpath("//ul[contains(@class, '_1Y6X2G3jjK')]//a[contains(text(),'Смартфоны')]")).click();
        //sort by price
        driver.findElement(By.xpath("//a[contains(text(),'по цене')]")).click();
        //check sort function
        driver.navigate().refresh();
        int forCompare = 0;
        List<WebElement> smartphone = driver.findElements(By.xpath("//div[contains(@class, 'n-snippet-cell2__main-price')]"));
        for (WebElement smartphones : smartphone) {
            String wholeString = smartphones.getText();
            String subString = wholeString.replaceAll(" ","");
            String result = subString.replaceAll("₽", "");
            System.out.println(result);
            int price = Integer.parseInt(result);
            assertThat(price, greaterThanOrEqualTo(forCompare));
            forCompare = price;
        }
    }

    @AfterTest
    public void closeDriver(){
        //logout
        driver.close();
    }

}
