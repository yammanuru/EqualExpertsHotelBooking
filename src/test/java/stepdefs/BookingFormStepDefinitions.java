package stepdefs;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pageObject.BookingPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookingFormStepDefinitions
{

    WebDriver driver = null;
    public final static String BASE_URL = "http://hotel-test.equalexperts.io/";
    public final static String CHROME_DRIVER_LOCATION = "chromedriver";

    @Before
    public void setUp()
    {
        driver = new ChromeDriver();
    }

    @After
    public void cleanUp()
    {
        driver.close();
    }

    @Then("^A new booking record is created$")
    public void a_new_booking_record_is_created()
    {
        BookingPage bookingPage = new BookingPage(driver);
        List<WebElement> items = bookingPage.getLastRow();
        assertEquals("TestFirstName", items.get(0).getText(), "Verifying First Name");
        assertEquals("TestLastName", items.get(1).getText(), "Verifying Last Name");
        assertEquals("60", String.valueOf(items.get(2).getText()), "Verifying Booking Price");
        assertEquals("true", items.get(3).getText(), "Verifying Booking Paid");
        assertEquals("2021-10-01", items.get(4).getText(), "Verifying Checkin Date");
        assertEquals("2021-11-01", items.get(5).getText(), "Verifying Check out Date");

    }


    @And("^user selects delete button$")
    public void userSelectsDeleteButton() throws InterruptedException
    {
        BookingPage bookingPage = new BookingPage(driver);
        bookingPage.deleteRow();
        Thread.sleep(2000);
    }

    @Then("^booking is deleted$")
    public void bookingIsDeleted()
    {
        BookingPage bookingPage = new BookingPage(driver);
        assertFalse(bookingPage.isBookingDeleted());

    }

    @Given("^A User visits the hotel booking form$")
    public void aUserVisitsTheHotelBookingForm() throws InterruptedException
    {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        Thread.sleep(1000);
    }

    @And("^The User submits valid booking data$")
    public void theUserSubmitsValidBookingData()
    {
        BookingPage bookingPage = new BookingPage(driver);
        bookingPage.fillDetails();
    }

    @And("^The User clicks on the save button$")
    public void theUserClicksOnTheSaveButton() throws InterruptedException
    {
        BookingPage bookingPage = new BookingPage(driver);
        bookingPage.clickSaveButton();
        Thread.sleep(5000);
    }

}
