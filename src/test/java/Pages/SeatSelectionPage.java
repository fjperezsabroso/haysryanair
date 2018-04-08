package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SeatSelectionPage extends PageObject{

    @FindBy(xpath = "//span[@class='seat-row-seat standard']")
    List<WebElement> allStandardSeats;

    @FindBy(xpath = "//span[. ='Next']")
    WebElement buttonContinue;

    @FindBy(xpath = "//span[. ='Confirm']")
    WebElement buttonConfirm;

    public SeatSelectionPage(WebDriver driver) {
        super(driver);
    }


    public void waitForLoading(){

        this.allStandardSeats = new WebDriverWait(this.driver,5).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//span[@class='seat-row-seat standard']"),0));

    }

    public boolean seatOptionsHaveLoaded(){

        try{
            this.waitForLoading();
            Thread.sleep(2000);
            return true;
        }catch(Exception e){}
        return false;

    }

    public void selectFirstNSeats(int seats){

        while(seats>0){
            this.allStandardSeats = new WebDriverWait(this.driver,5).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//span[@class='seat-row-seat standard']"),0));
            WebElement seat = this.allStandardSeats.get(seats-1);
            new WebDriverWait(this.driver,2).until(ExpectedConditions.elementToBeClickable(seat));
            seat.click();
            seats--;
        }
        this.clickContinueButton();
        this.clickConfirmButton();

    }

    public void clickContinueButton(){

        new WebDriverWait(this.driver,5).until(ExpectedConditions.elementToBeClickable(this.buttonContinue));
        this.buttonContinue.click();

    }

    public void clickConfirmButton(){

        new WebDriverWait(this.driver,5).until(ExpectedConditions.elementToBeClickable(this.buttonConfirm));
        this.buttonConfirm.click();

    }

}
