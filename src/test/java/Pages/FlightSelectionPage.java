package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightSelectionPage extends PageObject{


    WebElement buttonSelectFirstFlight;
    WebElement buttonContinue;

    public FlightSelectionPage(WebDriver driver) {
        super(driver);
    }


    public void waitForLoading(){

        this.buttonSelectFirstFlight = new WebDriverWait(this.driver,5).until(ExpectedConditions.presenceOfElementLocated(By.className("flight-header__min-price")));

    }

    public boolean bookingOptionsHaveLoaded(){

        try{
            this.waitForLoading();
            return true;
        }catch(TimeoutException e){}
        return false;

    }

    public void selectFirstFlight(){

        this.buttonSelectFirstFlight.click();

    }

    public void selectMaxPrice(){

        List<WebElement> priceOptions = new WebDriverWait(this.driver,5).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("flights-table-fares__head"),1));
        priceOptions.get(priceOptions.size()-2).click();

    }

    public void clickContinueButton(){

        this.buttonContinue = new WebDriverWait(this.driver,2).until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        this.buttonContinue.click();

    }

    public void selectDefaultOptions(){

        this.selectFirstFlight();
        this.selectMaxPrice();
        this.clickContinueButton();

    }

}
