package com.project.project.pages;

import com.project.project.components.PageObject;
import com.project.project.components.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InternetLoginPage extends PageObject {
    

    private static final String URL_INTERNET_LOGIN_PAGE = String.format(URL_INTERNET_TEMPLATE, INTERNET_HOST, INTERNET_PORT, INTERNET_CONTEXT, INTERNET_LINK);
    
    @FindBy(id = "username")
    private WebElement userNameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(css = "#login > button > i")
    private WebElement loginButton;
    @FindBy(id = "msgWarn")
    private WebElement loginMessage;

    public InternetLoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        getDriver().get(URL_INTERNET_LOGIN_PAGE);
    }
    
    public void openSpecificUrl(String url) {
        getDriver().get(url);
    }

    public InternetHomePage login() {
        getDriver().get(URL_INTERNET_LOGIN_PAGE);
        submitStandardCredentials();
        InternetHomePage InternetHomePage = clickSubmitButton();
        int loginCounter = 3;

        while (!isLogged(InternetHomePage) && loginCounter > 0) {
            loginCounter--;
            InternetHomePage = reLog();
        }

        if (isLogged(InternetHomePage)) {
        	TestBase.logger.info("Successfully logged into the application");
        } else {
            throw new PageException("Could not login to " + InternetHomePage.getClass());
        }

        return InternetHomePage;
    }

    private InternetHomePage reLog() {
        InternetHomePage InternetHomePage;
        open();
        submitStandardCredentials();
        InternetHomePage = clickSubmitButton();
        return InternetHomePage;
    }

    private boolean isLogged(InternetHomePage InternetHomePage) {
        return InternetHomePage.getPageSource().contains("Welcome");
    }

    private void submitStandardCredentials() {
        typeInto(userNameField, PageObject.ISSUER_ROLE_USERNAME);
        typeInto(passwordField, PageObject.ISSUER_ROLE_PASSWORD);
    }

    public InternetHomePage login(String user, String password) {
        getDriver().get(URL_INTERNET_LOGIN_PAGE);
        typeInto(userNameField, user);
        typeInto(passwordField, password);
        return clickSubmitButton();
    }
    


    public InternetHomePage clickSubmitButton() {
        clickButton(loginButton);
        return PageFactory.initElements(getDriver(), InternetHomePage.class);
    }
    

}
