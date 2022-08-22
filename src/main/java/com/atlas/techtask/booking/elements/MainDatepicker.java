package com.atlas.techtask.booking.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class MainDatepicker {
    @FindBy(xpath = "//*/div[contains(@class, 'next')]")
    private WebElement nextMonthBtn;
    @FindBy(xpath = "//div[@class = 'bui-calendar__wrapper']")
    private List<WebElement> calendarWrappers;
    @FindBy(xpath = "//div[contains(@class,'checkin')]")
    private WebElement openCalendarBtn;
    @FindBy(xpath = "//div[contains(@class,'bui-calendar')]")
    private WebElement calendar;
    private WebDriver webDriver;

    public MainDatepicker(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void pickDate(LocalDate date) {
        if (!isOpen()) {
            openCalendar();
        }
        switchToMonth(date.getMonth());
        selectDay(date);
    }

    public void openCalendar() {
        openCalendarBtn.click();
    }

    public boolean isOpen() {
        return calendar.isDisplayed();
    }

    public WebElement getCalendar() {
        return calendar;
    }

    private void switchToMonth(Month month) {
        String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        while (calendarWrappers.stream().noneMatch(mnth -> mnth.getText().contains(monthName))) {
            nextMonthBtn.click();
        }
    }

    private void selectDay(LocalDate date) {
        String monthName = date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        WebElement wrapperByMonth = getWrapperByMonth(monthName);
        List<WebElement> daysOfMonth = wrapperByMonth.findElements(By.xpath(".//td"));
        daysOfMonth
                .stream()
                .filter(day -> day.getText().equals(String.valueOf(date.getDayOfMonth())))
                .findFirst()
                .orElseThrow()
                .click();
    }

    private WebElement getWrapperByMonth(String monthName) {
        return calendarWrappers.stream()
                .filter(mnth -> mnth.getText().contains(monthName))
                .findFirst()
                .orElseThrow();
    }
}
