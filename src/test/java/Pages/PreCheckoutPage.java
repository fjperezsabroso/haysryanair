package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PreCheckoutPage extends PageObject{

    @FindBy(xpath = "//span[. ='Check out']/..")
    WebElement buttonCheckOut;

    WebElement buttonClosePopUp;

    public PreCheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean waitUntilUrlLoaded(){

        try{
            new WebDriverWait(this.driver,10).until(ExpectedConditions.urlMatches("https://www.ryanair.com/ie/en/booking/extras"));
            Thread.sleep(3000);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public void clickCheckOutButton(){

        new WebDriverWait(this.driver,5).until(ExpectedConditions.visibilityOf(this.buttonCheckOut));
        this.buttonCheckOut.click();
        this.closePopUp();

    }

    public void closePopUp(){

        try{
            this.buttonClosePopUp = new WebDriverWait(this.driver,2).until(ExpectedConditions.presenceOfElementLocated(By.className("popup-msg__close")));
            this.buttonClosePopUp.click();
        }catch(Exception e){}

    }

}
