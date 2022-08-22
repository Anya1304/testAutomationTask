package com.atlas.techtask.booking.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultPage {
    @FindBy(xpath = "//div[@*='property-card']")
    private List<WebElement> hotelsResultCards;
    @FindBy(xpath = "//*[@*='address']")
    private List<WebElement> hotelsAddress;
    @FindBy(xpath = "//button[@data-testid='date-display-field-start']")
    private WebElement chekInDateBtn;
    @FindBy(xpath = "//button[@data-testid='date-display-field-end']")
    private WebElement chekOutDateBtn;
    private WebDriver webDriver;

    public SearchResultPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getHotelsResultCards() {
        return hotelsResultCards;
    }

    public List<WebElement> getHotelsAddress() {
        return hotelsAddress;
    }

    public WebElement getChekInDateBtn() {
        return chekInDateBtn;
    }

    public WebElement getChekOutDateBtn() {
        return chekOutDateBtn;
    }
}
