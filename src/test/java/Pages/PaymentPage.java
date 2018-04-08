package Pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PaymentPage extends PageObject{

    @FindBy(xpath = "//input[contains(@id,'firstName')]")
    List<WebElement> textFirstNames;

    @FindBy(tagName = "passenger-search-list")
    List<WebElement> passengerSuggestions;

    @FindBy(id = "card-cvv-20")
    WebElement textCVV;

    @FindBy(xpath = "//label[contains(@for,'acceptTerms')]/span")
    WebElement checkBoxTerms;

    @FindBy(xpath = "//button[. = 'Pay Now']")
    WebElement buttonPay;

    @FindBy(xpath = "//div[@class='info-text' and contains(@translate,'error')]/span")
    WebElement spanErrorMessage;

    @FindBy(className = "after-pax-validation-step")
    WebElement paymentZone;

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean waitUntilUrlLoaded(){

        try{
            new WebDriverWait(this.driver,10).until(ExpectedConditions.urlMatches("https://www.ryanair.com/ie/en/booking/payment"));
            //Thread.sleep(2000);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public void fillOutPassengers(){

        for(int iterator=0; iterator<this.textFirstNames.size();iterator++){
            this.textFirstNames.get(iterator).click();
            WebElement sugestion = new WebDriverWait(this.driver,2).until(ExpectedConditions.visibilityOf(this.passengerSuggestions.get(iterator).findElement(By.className("person_desc"))));
            sugestion.click();
        }

    }

    public boolean completePayment(String cvv){

        boolean isThereCVV = false;
        try{
            new WebDriverWait(this.driver,3).until(ExpectedConditions.visibilityOf(this.textCVV));
            isThereCVV = true;
        }catch (TimeoutException e) {}
        if(!isThereCVV){
            try {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File("reports/images/paymenterror.png"));
            }catch (IOException e){}
        }else {
            this.textCVV.sendKeys(cvv);
            this.checkBoxTerms.click();
            this.buttonPay.click();
        }
        return isThereCVV;

    }

    public boolean isThereAnError(){

        try {
            //Wait for the payment to end
            new WebDriverWait(this.driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.className("box")));
            //Check for the error message to be present
            new WebDriverWait(this.driver, 1).until(ExpectedConditions.visibilityOf(this.spanErrorMessage));
            return true;
        }catch(TimeoutException e){
            return false;
        }

    }

    public String getPaymentError(){

        if(this.isThereAnError()){
            return this.spanErrorMessage.getText();
        }else{
            return null;
        }

    }

    public boolean pay(String cvv){

        this.waitUntilUrlLoaded();
        this.fillOutPassengers();
        return this.completePayment(cvv);
    }

}
