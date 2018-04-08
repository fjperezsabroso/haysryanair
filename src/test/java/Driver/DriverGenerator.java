package Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverGenerator {

    private ChromeOptions options;

    private WebDriver driver;

    public DriverGenerator(){
        super();
    }
    public WebDriver chromeDriverCreator(){

        //System.setProperty(driverPathProperty, driverPath);
        System.setProperty("webdriver.chrome.driver", "chromedriver");

        this.options = new ChromeOptions();
        //Avoid chrome information from automation status
        options.addArguments("disable-infobars");
        //Avoid notifications
        options.addArguments("--disable-notifications");
        //Headless option to optimize running times
        //options.addArguments("headless");
        //FullScreen
        options.addArguments("--start-fullscreen");
        this.driver = new ChromeDriver(this.options);
        return this.driver;
    }

    public WebDriver firefoxDriverCreator(){
        /*TODO*/
        return this.driver;
    }

    public WebDriver remoteDriverCreator(){
        /*TODO*/
        /*
        * The change from using a local webdriver and a remote one, is just using a hub already configured passing also
        * the desired capabilities for the driver we want to use
        * this.driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),DesiredCapabilities.firefox());
        * */
        return this.driver;
    }
}
