package Pages;

import BasePackage.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WeatherHomePage extends BaseClass {

    @FindBy(css = "#searchform input")
    private WebElement inputCity;

    @FindBy(css = "button[class*='orange']")
    private WebElement searchButton;

    @FindBy(css = "div#forecast_list_ul a[href*='city']")
    private WebElement searchResultCityLink;

    @FindBy(xpath = "//td[contains(text(),'Pressure')]//following-sibling::td")
    private WebElement pressureValue;

    @FindBy(xpath = "//td[contains(text(),'Humidity')]//following-sibling::td")
    private WebElement humidityValue;

    @FindBy(xpath = "//td[contains(text(),'Cloudiness')]//following-sibling::td")
    private WebElement cloudinessValue;

    @FindBy(xpath = "//td[contains(text(),'Wind')]//following-sibling::td")
    private WebElement windValue;


    public WeatherHomePage() {
        PageFactory.initElements(driver, this);
    }

    public WeatherHomePage selectCityInSearchResult() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(searchResultCityLink)).click();
        return this;
    }

    public WeatherHomePage clickOnSearchButton() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        return this;
    }

    public WeatherHomePage searchWithCityName(String cityName) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCity)).sendKeys(cityName);
        return this;
    }

    public String getPressureValue() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(pressureValue.getText());
        return pressureValue.getText();
    }

    public String getHumidityValue() {
        return humidityValue.getText();
    }

    public String getCloudinessValue() {
        System.out.println(cloudinessValue.getText());
        return cloudinessValue.getText();
    }

    public String getWindValue() {
        return windValue.getText();
    }

}
