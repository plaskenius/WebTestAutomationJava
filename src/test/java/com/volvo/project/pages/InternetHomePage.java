package com.project.project.pages;

import com.aventstack.extentreports.Status;
import com.project.project.components.PageObject;
import com.project.project.components.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InternetHomePage extends PageObject {

    private static final String SEARCH_system_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";

    @FindBy(id = "flash")
    public WebElement Header;
    @FindBy(css = "#content > div > a > i")
    private WebElement _logoutButton;
    @FindBy(css = "div > a > i")
    private WebElement releaseVersion;
    @FindBy(id = "logoutlink")
    private WebElement logoutLink;


    @SuppressWarnings("rawtypes")
    public void getAppVersion() {
        try {
            System.setProperty("ActualVersion", releaseVersion.getText());
        } catch (NoSuchElementException e) {
            TestBase.logger.info("cannot find ReadVersion element");
        }
    }


    //----------------

    public InternetHomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded(String name) {
        try {
            getDriver().findElement(By.xpath("//h4[text()='"+SEARCH_system_FORM_NAME+"']"));
        } catch (NoSuchElementException e) {
            logger.info("User " + name + " has not been author PawelPieized");
            child.get().log(Status.INFO, "User " + name + " has not been author PawelPieized");
            return false;
        }
        return true;
    }

    public InternetHomePage openLink(String link) {
        getDriver().get(link);
        return PageFactory.initElements(getDriver(), InternetHomePage.class);
    }
    

    

  /*  public boolean verifyBodyTextContains(String expectedResult) {
        waitForTextToBePresentInWebElement(byHomePageBody, expectedResult);
        return getWebElementText(homePageBody).contains(expectedResult);
    }
    
    public boolean verifyTextOnEditDraftPage(String expectedResult) {
        waitForTextToBePresentInWebElement(byDraftStatus, expectedResult);
        return getWebElementText(draftStatus).contains(expectedResult);
    }*/


    public void logout() {
        try {
            moveOnMenu(logoutLink);
            clickElement(logoutLink);
        } catch (Exception e) {
            TestBase.logger.info("cannot find logout button");

        }

       /* catch (TimeoutException e){
            getDriver().findElement(By.cssSelector("html body div#panelSet.horizontalPanels div#headerContent div#branding span.supportingFunctions form#lf a")).click();
        }*/
    }

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 15);
        wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();
        sleepForWhile(1);
    }

    private void sleepForWhile(int count) {
        try {
            for (int i = 0; i < count; i++) {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
        }
    }

}
