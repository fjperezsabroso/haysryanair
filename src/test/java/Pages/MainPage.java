package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage extends PageObject{


    @FindBy(xpath="//input[@placeholder='Departure airport']")
    WebElement textOriginSelector;

    @FindBy(xpath="//input[@placeholder='Destination airport']")
    WebElement textDestinationSelector;

    @FindBy(xpath="//button[@ng-keypress='searchFlights()']")
    WebElement buttonLetsGo;


    @FindBy(className = "cookie-popup__close-btn")
    WebElement closeCookiePolicy;

    @FindBy(id = "flight-search-type-option-one-way")
    WebElement radioButtonOneWay;

    //Date
    @FindBy(className = "dd")
    WebElement textDaySelector;

    @FindBy(className = "mm")
    WebElement textMonthSelector;

    @FindBy(className = "yyyy")
    WebElement textYearSelector;

    //@FindBy(xpath = "//*[@id=\"row-dates-pax\"]/div[2]/div[2]/div[2]/div/div[2]")
    @FindBy(className = "dropdown-handle")
    WebElement selectNumPassengers;

    @FindBy(id = "myryanair-auth-login")
    WebElement buttonLogin;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void closeCookiePolicy(){

        if(this.closeCookiePolicy != null && this.closeCookiePolicy.isDisplayed()){
            this.closeCookiePolicy.click();
        }

    }

    public void selectOneWayFlight(String origin, String destination, String day, String month, String year, int adults, int teens, int children, int infant){

        this.selectOneWayFlight();
        this.selectFlightAirports(origin, destination);
        this.selectDepartureDate(day, month, year);
        this.selectPassengers(adults,teens,children,infant);
        this.clickLetsGoButton();

    }

    public void selectOneWayFlight(){

        this.radioButtonOneWay.click();

    }

    public void selectFlightAirports(String origin, String destination){

        this.textOriginSelector.clear();
        this.textOriginSelector.sendKeys(origin);
        this.textDestinationSelector.sendKeys(destination);
        WebElement result = new WebDriverWait(this.driver,2).until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//span[. ='" + destination + "']"))));
        result.click();

    }

    public void selectDepartureDate(String day, String month, String year){

        this.textDaySelector.sendKeys(day);
        this.textMonthSelector.sendKeys(month);
        this.textYearSelector.sendKeys(year);
        //This tab enter is to avoid the focus staying in the date selector
        this.textYearSelector.sendKeys(Keys.TAB);

    }

    public void selectPassengers(int adults, int teens, int children, int infant){

        this.selectNumPassengers.click();
        List<WebElement> buttonPlusPassenger= new WebDriverWait(this.driver,2).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("inc"),0));
        while(adults>1){
            buttonPlusPassenger.get(0).click();
            adults--;
        }
        while(teens > 0){
            buttonPlusPassenger.get(1).click();
            teens--;
        }
        while(children > 0){
            buttonPlusPassenger.get(2).click();
            children--;
        }
        while(infant > 0){
            buttonPlusPassenger.get(3).click();
            infant--;
        }

    }

    public void login(String email, String password){
        this.clickLoginButton();
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.submitLogin(email,password);
    }

    public void clickLoginButton(){

        this.buttonLogin.click();

    }

    public void clickLetsGoButton(){

        this.buttonLetsGo.click();

    }
}
