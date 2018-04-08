package TestCases;

import Driver.DriverGenerator;
import Pages.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.fail;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    WebDriver driver;
    MainPage mainPage;
    ExtentReports extent;
    ExtentTest testReport;

    @BeforeAll
    public void loggerInit(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        String extentPath = "reports";
        String extentReportFile = extentPath + "/" + dateFormat.format(date) + ".html";
        this.extent = new ExtentReports(extentReportFile, false);

    }

    @BeforeEach
    public void driverInitiation(TestInfo testInfo){

        this.testReport = extent.startTest(testInfo.getDisplayName());
        this.testReport.log(LogStatus.INFO, "Creating Chrome Driver");
        this.driver = new DriverGenerator().chromeDriverCreator();
        this.testReport.log(LogStatus.INFO, "Navigating to 'https://www.ryanair.com/ie/en/'");
        this.driver.get("https://www.ryanair.com/ie/en/");
        this.mainPage = new MainPage(this.driver);
        this.mainPage.closeCookiePolicy();

    }
    @Test
    @DisplayName("Flight Booking")
    public void testFlightBooking(){

        //MainPage Phase
        this.testReport.log(LogStatus.INFO, "Logging in as Testing Tester");
        this.mainPage.login("fj.perez.sabroso+1@gmail.com","Password01");
        this.testReport.log(LogStatus.INFO, "Selecting a flight Madrid->Brussels on 14/05/18 for 2 adults and 1 child");
        try {
            this.mainPage.selectOneWayFlight("Madrid", "Brussels",
                    "14", "05", "2018",
                    2, 0, 1, 0);
        }catch(Exception e){
            try{
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File("reports/images/paymenterror.png"));
                this.testReport.log(LogStatus.FATAL, "Payment method not found: " + this.testReport.addScreenCapture("images/paymenterror.png"));

            }catch(Exception i){}
        }
        //FlightSelectionPage Phase
        FlightSelectionPage flightSelectionPage = new FlightSelectionPage(this.driver);
        //I could be using asserts but this makes the code clearer for logging purposes
        if(!flightSelectionPage.bookingOptionsHaveLoaded()){
            this.testReport.log(LogStatus.FAIL, "There was an error while loading the available flights");
            fail("There was an error while loading the available flights");
        }
        this.testReport.log(LogStatus.INFO, "Selecting the first flight option with the maximum available options");
        flightSelectionPage.selectDefaultOptions();

        //SeatSelectionPage Phase
        SeatSelectionPage seatSelectionPage = new SeatSelectionPage(this.driver);
        if(!seatSelectionPage.seatOptionsHaveLoaded()){
            this.testReport.log(LogStatus.FAIL, "There was an error while loading the available seats");
            fail("There was an error while loading the available seats");
        }
        this.testReport.log(LogStatus.INFO, "Selecting the first 3 standard seats");
        seatSelectionPage.selectFirstNSeats(3);

        //PreCheckoutPage Phase
        PreCheckoutPage preCheckoutPage = new PreCheckoutPage(this.driver);
        if(!preCheckoutPage.waitUntilUrlLoaded()){
            this.testReport.log(LogStatus.FAIL, "There was an error while loading the pre checkout page");
            fail("There was an error while loading the pre checkout page");
        }
        this.testReport.log(LogStatus.INFO, "Starting the checkout process");
        preCheckoutPage.clickCheckOutButton();

        this.testReport.log(LogStatus.INFO, "Trying to checkout with a false card number to verify the error message");
        PaymentPage paymentPage = new PaymentPage(this.driver);
        if(!paymentPage.pay(Integer.toString(((int) Math.floor(100+Math.random()*899))))){
            this.testReport.log(LogStatus.FATAL, "Payment method not found: " + this.testReport.addScreenCapture("images/paymenterror.png"));
            fail("No payment options were shown");
        }else {
            String error = paymentPage.getPaymentError();
            if (error == null) {
                this.testReport.log(LogStatus.FAIL, "An error was expected but none was found");
                fail("An error was expected but none was found");
            }
            this.testReport.log(LogStatus.PASS, error);
        }
    }

    @AfterEach
    public void driverDeletion(){

        //Commented to checkout the results
        this.driver.quit();
        this.testReport.log(LogStatus.INFO, "Browser closed");
        extent.endTest(this.testReport);

    }

    @AfterAll
    public void flushReport(){

        this.extent.flush();

    }

}
