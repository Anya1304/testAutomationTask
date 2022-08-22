package com.atlas.techtask.booking;

import com.atlas.techtask.TestReporterUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.atlas.techtask.booking.pages.BookingHomePage;
import com.atlas.techtask.booking.pages.SearchResultPage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTest {
    private static WebDriver webDriver;
    private static final String ADDRESS = "New York";
    private static final String URL = "https://www.booking.com/";
    private static BookingHomePage homePage;
    private static SearchResultPage resultPage;
    private static final LocalDate dateFrom = LocalDate.of(2022, 12, 1);
    private static final LocalDate dateTo = LocalDate.of(2022, 12, 30);

    @BeforeAll
    static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        webDriver = new ChromeDriver();
        homePage = new BookingHomePage(webDriver);
    }

    @AfterEach
    public void afterEach(){
        webDriver.manage().deleteAllCookies();
    }

    @AfterAll
    static void afterAll() throws IOException {
        Set<Method> methods = TestReporterUtils.getMethodsAnnotatedWith(Test.class, BookingTest.class.getPackageName());
        TestReporterUtils.printMethodsToFile(methods, "bookingTestMethods.csv");
    }

    @Test
    @Order(1)
    public void searchHotelsInCityByDate() {
        webDriver.navigate().to(URL);
        homePage.setAddressLbl(ADDRESS);
        homePage.setCheckInDate(dateFrom);
        homePage.setCheckOutDate(dateTo);

        resultPage = homePage.getSearchResult();
        assertTrue(resultPage.getHotelsAddress()
                .stream()
                .allMatch(address -> address.getText().contains(ADDRESS)));

        String monthFromName = dateFrom.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        String monthToName = dateTo.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        String checkInBtnText = resultPage.getChekInDateBtn().getText();
        String checkOutBtnText = resultPage.getChekOutDateBtn().getText();

        assertTrue(checkInBtnText.contains(monthFromName)
                && checkInBtnText.contains(String.valueOf(dateFrom.getDayOfMonth()))
                && checkInBtnText.contains(String.valueOf(dateFrom.getYear())));
        assertTrue(checkOutBtnText.contains(monthToName)
                && checkOutBtnText.contains(String.valueOf(dateTo.getDayOfMonth()))
                && checkOutBtnText.contains(String.valueOf(dateTo.getYear())));
    }

    @Test
    @Order(2)
    public void searchHotelsWithNoCity() {
        webDriver.navigate().to(URL);
        homePage.setCheckInDate(dateFrom);
        homePage.setCheckOutDate(dateTo);

        resultPage = homePage.getSearchResult();
        assertTrue(homePage.getAlertMessage().isDisplayed());
        assertTrue(homePage.getAlertMessage().getText().contains("destination to start searching."));
    }

    @Test
    @Order(3)
    public void searchHotelsWithNoPeriodDate() {
        webDriver.navigate().to(URL);
        homePage.setAddressLbl(ADDRESS);

        resultPage = homePage.getSearchResult();
        assertTrue(resultPage.getChekInDateBtn().getText().contains("Check-in Date"));
        assertTrue(resultPage.getChekOutDateBtn().getText().contains("Check-out Date"));
    }
}
