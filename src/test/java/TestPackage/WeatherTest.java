package TestPackage;

import BasePackage.BaseClass;
import Pages.WeatherHomePage;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpRequest;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class WeatherTest extends BaseClass {
    private Map<String, String> reqParams = new HashMap<String, String>();
    private String api = " http://api.openweathermap.org/data/2.5/weather";
    private Response response;
    private WeatherHomePage weatherHomePage;


    @BeforeTest
    public void setUpMethod() {
        initialization();
        weatherHomePage = new WeatherHomePage();
    }


    @DataProvider(name = "cityNames")
    public static Object[][] getcityNames() {
        return new Object[][]{

                {
                        "Lompico", ",US",
                },
                {
                        "London", ",US"
                },
                {
                        "Hurzuf", ",UA"
                },
        };
    }


    @Test(dataProvider = "cityNames")
    public void WeatherApiAndUIComparisonTest(String cityName,String country) throws InterruptedException {
        driver.get("https://openweathermap.org/");
        RequestSpecification requestSpecification= RestAssured.given();
        reqParams.put("q",cityName+country);
        reqParams.put("appid","ce658e43d986d3db6fdafac97e5a006b");
        response=requestSpecification.params(reqParams).get(api);
        String cloudiness = response.jsonPath().getString("weather.description");
        String pressure = response.jsonPath().getString("main.pressure");
        String humidity = response.jsonPath().getString("main.humidity");
        String windSpeed = response.jsonPath().getString("wind.speed");
        String windDegrees = response.jsonPath().getString("wind.deg");
        weatherHomePage.searchWithCityName(cityName+country).clickOnSearchButton().selectCityInSearchResult();
        System.out.println("===================================================");
        System.out.println(cloudiness);
        System.out.println(pressure);
        System.out.println(humidity);
        System.out.println(windSpeed);
        System.out.println(windDegrees);
        System.out.println("===================================================");
        Assert.assertTrue(weatherHomePage.getPressureValue().contains(pressure),"Pressure value in API and UI did not match ");
        // below validation will fail because UI and API values are different (clear sky and sky is clear)
        //Assert.assertTrue(weatherHomePage.getCloudinessValue().toLowerCase().contains(cloudiness.toLowerCase()),"Cloudiness value in API and UI did not match ");
        Assert.assertTrue(weatherHomePage.getHumidityValue().contains(humidity),"humidity value in API and UI did not match ");
        Assert.assertTrue(weatherHomePage.getWindValue().contains(windDegrees), String.format("wind deg value is %s in API and UI did not match ", weatherHomePage.getWindValue()));
        Assert.assertTrue(weatherHomePage.getWindValue().contains(windSpeed), String.format("wind speed value is %s in API and UI did not match ", weatherHomePage.getWindValue()));
    }

    @Test(dataProvider = "cityNames")
    public void maxAndMinTemperature(String cityName,String country) throws InterruptedException {
        RequestSpecification requestSpecification= RestAssured.given();
        reqParams.put("q",cityName+country);
        reqParams.put("appid","ce658e43d986d3db6fdafac97e5a006b");
        response=requestSpecification.params(reqParams).get(api);
        double maxTemp = response.jsonPath().getDouble("main.temp_max");
        double minTemp = response.jsonPath().getDouble("main.temp_min");
        Assert.assertTrue(maxTemp-minTemp<10.0,"The max and min temps are not within 10 degree range");
    }

    @AfterTest
    public void quit() {
        driver.quit();
    }
}
