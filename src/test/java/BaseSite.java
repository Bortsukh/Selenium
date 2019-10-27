import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class BaseSite {
    protected WebDriver driver;

    @FindBy(xpath = "//a[contains(text(),'Маркет')]")
    private WebElement market;

    @FindBy(className = "n-w-tab__control-hamburger")
    private WebElement listOfKinds;

    @FindBy(xpath="//span[@class='n-w-tab__control-caption'][contains(text(),'Электроника')]")
    private WebElement electronic;


    @FindBy(id = "glpricefrom")
    private WebElement priceFrom;


    protected BaseSite(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void openMarket()
    {
        market.click();
    }

    public void openListofKinds()
    {
        listOfKinds.click();

    }

    public void openElectronicCatalogue()
    {
        electronic.click();

    }

    public void setPriceFrom(final String price)
    {
        priceFrom.sendKeys(price);

    }


}

