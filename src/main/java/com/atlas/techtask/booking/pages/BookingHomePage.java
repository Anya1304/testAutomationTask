package com.atlas.techtask.booking.pages;

import com.atlas.techtask.booking.modules.MainDatepicker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;

public class BookingHomePage {
    @FindBy(xpath = "//*[contains(@*, 'title1')]")
    private WebElement pageTitle;
    @FindBy(xpath = "//*[@type = 'search']/parent::label")
    private WebElement addressLbl;
    @FindBy(xpath = "//*[contains(text(), 'Search')]/parent::button")
    private WebElement searchBtn;
    @FindBy(xpath = "//*[@role = 'alert' and contains(@class, 'red')]")
    private WebElement alertMessage;
    @FindBy
    private MainDatepicker datepicker;

    private WebDriver webDriver;

    public BookingHomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        datepicker = new MainDatepicker(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getAddressLbl() {
        return addressLbl;
    }

    public void setAddressLbl(String address) {
        addressLbl.sendKeys(address);
    }

    public void setCheckInDate(LocalDate dateFrom) {
        setDatepickerDate(dateFrom);
    }

    public void setCheckOutDate(LocalDate dateTo) {
        setDatepickerDate(dateTo);
    }

    public SearchResultPage getSearchResult() {
        searchBtn.click();
        return new SearchResultPage(webDriver);
    }

    private void setDatepickerDate(LocalDate date) {
       datepicker.pickDate(date);
    }

    public WebElement getAlertMessage() {
        return alertMessage;
    }
    public WebElement getDatepicker(){
        return datepicker.getCalendar();
    }
}
