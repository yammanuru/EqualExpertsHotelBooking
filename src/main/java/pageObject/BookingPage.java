package pageObject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BookingPage
{

    WebDriver driver;

    public BookingPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> deletedIds = new ArrayList<>();
    public String id;
    @FindBy(xpath = "/html//input[@id='firstname']")
    private WebElement first_name;

    @FindBy(xpath = "/html//input[@id='lastname']")
    private WebElement last_name;

    @FindBy(xpath = "/html//input[@id='totalprice']")
    private WebElement price;

    @FindBy(xpath = "/html//select[@id='depositpaid']")
    private WebElement deposit;

    @FindBy(xpath = "/html//input[@id='checkin']")
    private WebElement check_in;

    @FindBy(xpath = "/html//input[@id='checkout']")
    private WebElement check_out;

    @FindBy(xpath = "//div[@id='form']/div[@class='row']//input[@value=' Save ']")
    private WebElement saveBookingButton;


    /**
     * Method inputs first name in the input field, booking form - Firstname
     */
    public void enterFirstName(String firstname)
    {
        this.first_name.sendKeys(firstname);
    }

    /**
     * Method inputs last name in the input field, booking form - Surname
     */
    public void enterLastName(String lastName)
    {
        this.last_name.sendKeys(lastName);
    }

    /**
     * Method inputs price in the input field, booking form - Price
     */
    public void enterPrice(double bookingPrice)
    {
        this.price.sendKeys(String.valueOf(bookingPrice));
    }

    /**
     * Method inputs true or false in the input field, booking form - Deposit
     */
    public void setDepositPaid(String bookingpaid)
    {
        Select depositPaidDropDown = new Select(driver.findElement(By.xpath("/html//select[@id='depositpaid']")));
        depositPaidDropDown.selectByVisibleText(bookingpaid);
    }

    /**
     * Method inputs checkin date in the input field, booking form - checkin
     */
    public void selectCheckInDate(String checkInDate)
    {
        check_in.sendKeys(checkInDate);
    }

    /**
     * Method inputs checkout date in the input field, booking form - checkout
     */
    public void selectCheckOutDate(String checkOutDate)
    {
        check_out.sendKeys(checkOutDate);
    }

    /**
     * Method performs click action on booking form - save button
     */
    public void clickSaveButton()
    {
        saveBookingButton.click();
    }

    /**
     * Method retrieves all the webelements from the last booking record and
     *
     * @return All the webelements from the last booking record
     */
    public List<WebElement> getLastRow()
    {
        List<WebElement> rows = driver.findElement(By.id("bookings")).findElements(By.className("row"));
        WebElement currentRow = rows.get(rows.size() - 1);
        return currentRow.findElements(By.tagName("P"));
    }

    /**
     * Method deletes the last booking record
     */
    public void deleteRow()
    {
        id = getIdOfRowCreated();
        WebElement parentBooking = driver.findElement(By.id("bookings"));
        WebElement requiredBooking = parentBooking.findElement(By.id(id));
        WebElement deleteButton = requiredBooking.findElement(By.tagName("input"));
        deleteButton.click();
        deletedIds.add(String.valueOf(id));
    }

    /**
     * Method verifies, if any of the elements from deletedIds list exist in current form ids list and
     *
     * @return true or false
     */
    public boolean isBookingDeleted()
    {
        return deletedIds.stream()
                         .filter(element -> getAllIdsFromBookingForm().contains(element))
                         .findFirst()
                         .isPresent();
    }

    /**
     * Method fills the booking form details
     */

    public void fillDetails()
    {
        enterFirstName("TestFirstName");
        enterLastName("TestLastName");
        enterPrice(60);
        setDepositPaid("true");
        selectCheckInDate("2021-10-01");
        selectCheckOutDate("2021-11-01");

    }

    /**
     * Method gets the last created booking id
     *
     * @return booking id from the form html
     */
    public String getIdOfRowCreated()
    {
        WebElement parentBooking = driver.findElement(By.id("bookings"));
        List<WebElement> rows = parentBooking.findElements(By.className("row"));
        List<Integer> idList = new ArrayList<>();
        if (rows.size() > 1)
        {
            for (int i = 1; i <= rows.size() - 1; i++)
            {
                idList.add(Integer.parseInt(rows.get(i).getAttribute("id")));
            }
            Integer id = idList.stream().max(Integer::compareTo).get();
            return String.valueOf(id);
        }
        else
        {
            return String.valueOf(0);
        }
    }

    /**
     * Method gets all the ids from the form html
     *
     * @return list of all ids
     */
    public List<String> getAllIdsFromBookingForm()
    {
        WebElement parentBooking = driver.findElement(By.id("bookings"));
        List<WebElement> rows = parentBooking.findElements(By.className("row"));
        List<String> idList = new ArrayList<>();
        if (rows.size() >= 2)
        {
            for (int i = 1; i <= rows.size() - 1; i++)
            {
                idList.add(rows.get(i).getAttribute("id"));
            }
            return idList;
        }
        return null;
    }


}
