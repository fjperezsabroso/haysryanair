package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends PageObject{

    @FindBy(id = "email393")
    WebElement textEmail;

    @FindAll({
            @FindBy(id = "password419"),
            @FindBy(id = "password420"),
            @FindBy(id = "password424")
    })
    WebElement textPassword;

    @FindBy(xpath = "//button-spinner[@button-text='MYRYANAIR.AUTHORIZATION.LOGIN.LOGIN_AUTHORIZATION']/button")
    WebElement buttonLonIn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public void submitLogin(String email, String password){

        this.textEmail = new WebDriverWait(this.driver,2).until(ExpectedConditions.elementToBeClickable(By.id("email393")));
        this.textEmail.clear();
        this.textEmail.sendKeys(email);
        this.textPassword.clear();
        this.textPassword.sendKeys(password);
        this.buttonLonIn.click();
        try{
            Thread.sleep(2000);
        }catch(Exception e){}

    }
}
