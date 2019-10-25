package com.project.project.components;

import org.aspectj.weaver.ast.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * This class contains methods representing actions which can be performed by a user on the webpage.
 * These methods are used in classes representing Page Objects.
 *
 * @author PawelPie
 */
public class PageObject extends TestBase{

    public static final String URL_INTERNET_TEMPLATE = "http://%s:%s/%s";
    public static final String INTERNET_LINK = System.getProperty("INTERNET_LINK", "");
    public static final String INTERNET_HOST = System.getProperty("INTERNET_HOST", "the-internet.herokuapp.com");
    public static final String INTERNET_PORT = System.getProperty("INTERNET_PORT", "");
    public static final String INTERNET_CONTEXT = System.getProperty("INTERNET_CONTEXT", "login");
    public static final String INTERNET_DB = System.getProperty("INTERNET_DB", "gorabas17.srv.project.com:1523/gsystemt01.srv.project.com");

    public static final String INTERNET_DB_INTERNETDBA_USER = System.getProperty("INTERNETDBA_USER", "INTERNETDBA");
    public static final String INTERNET_DB_INTERNETDBA_PASS = System.getProperty("INTERNETDBA_PASS", "psw_systemdba456");
//    public static final String INTERNET_DB_INTERNETDBA_PASS = System.getProperty("INTERNETDBA_PASS", "psw_systemdba654");
//    public static final String INTERNET_DB_INTERNETDBA_PASS = System.getProperty("INTERNETDBA_PASS", "psw_systemdba321");

    //Users logins and passwords
    public static final String ISSUER_ROLE_USERNAME = "cs-ws-s-INTERNET_ISSUER";
    public static final String ISSUER_ROLE_PASSWORD = "P8cO6fRL";

    public static final String CHAIRMAN_ROLE_USERNAME = "cs-ws-s-InternetChairman";
    public static final String CHAIRMAN_ROLE_PASSWORD = "PcPg9ffe";

    public static final String CHAIRMANONLY_ROLE_USERNAME = "cs-ws-s-chairmanonly";
    public static final String CHAIRMANONLY_ROLE_PASSWORD = "&L-999ade9L9L&8";

    public static final String TEAMMEMBER_ROLE_USERNAME = "cs-ws-s-PctTeamMemb";
    public static final String TEAMMEMBER_ROLE_PASSWORD = "I87MQfK8";

    public static final String SUPERUSER_ROLE_USERNAME = "cs-ws-s-SuperUser";
    public static final String SUPERUSER_ROLE_PASSWORD = "gPK8P8Ne";

    public static final String STAKEHOLDER_ROLE_USERNAME = "cs-ws-s-StakeHolder";
    public static final String STAKEHOLDER_ROLE_PASSWORD = "a79LgKOM";
    private static final int WAIT_1_SECOND = 1000;
    private static final String VALUE_ATTRIBUTE = "value";
    private static final int WAIT_HALF_SECOND = 500;
    private static final int SLEEP_INTERVAL = 500;
    private static final int CHECKBOX_SELECTION_RETRY_COUNT = 5;
    private static final int TIME_OUT_2_SECONDS = 2;
    private static final int TIME_OUT_5_SECONDS = 5;
    private static final int TIME_OUT_10_SECONDS = 10;
    private static final int TIME_OUT_30_SECONDS = 30;
    private static final int TIME_OUT_40_SECONDS = 40;



//  private WebDriver driver;
//
//  public WebDriver getDriver() {
//    return driver;
//  }

    public PageObject(WebDriver driver) {
        this.driver.set(driver);
    }

    protected void typeInto(WebElement field, String value) {
        try {
            waitForWaitModalNotPresent();
        } catch (Exception e1) {

        }
        try {
            try {
                waitForElement(field);
                field.sendKeys(value);
            } catch (StaleElementReferenceException e) {
                waitForElement(field).sendKeys(value);
            }
        } catch (Exception e) {
            waitForElement(field).sendKeys(value);
        }
    }

    protected void clickPageUp(WebElement field) {
        waitForElement(field).sendKeys(Keys.PAGE_UP);
    }

    protected void clickPageDown(WebElement field) {
        waitForElement(field).sendKeys(Keys.PAGE_DOWN);
    }


    protected void clickCtrlQ(WebElement field) {
        //waitForElement(field).sendKeys(Keys.LEFT_CONTROL + "q");
        waitForElement(field).sendKeys(Keys.chord(Keys.CONTROL, "q"));
    }

    protected void clickEnter(WebElement field) {
        waitForElement(field).sendKeys(Keys.ENTER);
    }

    protected void clickSpace(WebElement field) {
        waitForElement(field).sendKeys(Keys.SPACE);
    }

    protected void clickTab(WebElement field) {
        waitForElement(field).sendKeys(Keys.TAB);
    }

    protected void clearField(WebElement field) {
        waitForWaitModalNotPresent();
        try {
            waitForElement(field).clear();
        } catch (StaleElementReferenceException e) {
            waitForElement(field).clear();
        }
    }

    protected void typeInto(WebElement field, int value) {
        waitForElement(field).sendKeys(Integer.toString(value));
    }


    protected void clickButton(WebElement button) {
        try {
            waitForWaitModalNotPresent();
            waitUntilIsVisible(button);
            waitForElement(button).click();
            waitForWaitModalNotPresent();
        } catch (WebDriverException e) {
            waitForElement(button).click();
        }
    }

    private boolean acceptNextAlert = true;

    public void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 15);
        wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().dismiss();
    }
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = getDriver().switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    protected void clickButtonLocatedBy(By button) {
        waitForWaitModalNotPresent();
        waitForElementLocatedBy(button).click();
    }

    protected void moveOnMenu(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(waitForElement(element));
        actions.perform();
    }

    public void doubleClickElement(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.doubleClick(waitForElement(element));
        actions.perform();
    }

    // should be better way - to modify
    public void setDelayForAccessToSelectBoxValue(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            TestBase.logger.debug(e.toString());
        }
    }

    /**
     * @param blankFirstValue
     * @param index
     * @return index of first valid element on the list
     */
    private int adjustIndex(boolean blankFirstValue, int index) {
        int i = index;
        if (blankFirstValue && i == 0) {
            i += 1;
        }
        return i;
    }

    /**
     * @param webElements
     * @return list of WebElements
     */
    public List<String> extractTextFromWebElementList(List<WebElement> webElements) {
        List<String> webElementList = new ArrayList<String>();
        for (WebElement webElement : webElements) {
            webElementList.add(webElement.getText());
        }
        return webElementList;
    }

    /**
     * @param field
     * @return text from the specified WebElement
     * If the WebElement is not found on the page, an empty String ("") is returned
     * Waits for the WebElement for 10 seconds
     */
    public String getWebElementText(WebElement field) {
        try {
            try {
                TestBase.logger.info("ActualWeek:" + waitForElement(field).getText());
                return waitForElement(field).getText();
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getDropdownText(WebElement field) {
        try {
            try {
                return waitForElement(field).getText();
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean verifyText(String actualText, WebElement obj) {
        try {
            try {
                //matches case
                if (waitForElement(obj).getText().equals(actualText)) {
                    return true;
                } else {
                    return false;
                }
            } catch (NoSuchElementException e) {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @param field
     * @return text from the specified WebElement
     * If the WebElement is not found on the page, an empty String ("") is returned
     * Waits for the WebElement for 2 seconds
     */
    public String getWebElementTextWaitFor2Sec(By field) {
        try {
            try {
                return waitForElementLocatedByFor2Sec(field).getText();
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getWebElementTextAndRemoveFormatting(WebElement field) {
        try {
            try {
                return waitForElement(field).getText().replaceAll("[^\\d\\-]", "");
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    /**
     * @param field
     * @return text from the element defined by a By locator
     * If the element is not found on the page, an empty String ("") is returned
     * Waits for the WebElement for 10 seconds
     */
    public String getElementText(By field) {
        try {
            try {
                try {
                    return waitForElement(getDriver().findElement(field)).getText();
                } catch (StaleElementReferenceException e) {
                    try {
                        Thread.sleep(WAIT_1_SECOND);
                    } catch (InterruptedException e1) {
                    }
                    return waitForElement(getDriver().findElement(field)).getText();
                }
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    /**
     * @param field
     * @return text from the element defined by a By locator
     * Waits for the WebElement for 10 seconds
     */
    public String getElementTextNoExceptionCatching(By field) {
        return waitForElement(getDriver().findElement(field)).getText();
    }

    public String getElementTextAndRemoveFormatting(By field) {
        try {
            try {
                return waitForElement(getDriver().findElement(field)).getText().replaceAll("[^\\d\\-]", "");
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    /**
     * @param field
     * @return text from the element defined by a By locator
     * If the element is not found on the page, an empty String ("") is returned
     * Waits for the WebElement for 30 seconds
     */
    public String getElementTextWait30Seconds(By field) {
        try {
            try {
                return waitForElement30Seconds(getDriver().findElement(field)).getText();
            } catch (NoSuchElementException e) {
                return "";
            }
        } catch (TimeoutException e) {
            return "";
        }
    }

    /**
     * @param webElement
     * @return value of WebElement's "value" attribute
     */
    public String getValueAttribute(WebElement webElement) {
        return waitForElement(webElement).getAttribute(VALUE_ATTRIBUTE).trim();
    }

    public String getValueAttributeOfHiddenField(WebElement webElement) {
        return webElement.getAttribute(VALUE_ATTRIBUTE);
    }

    /**
     * @param by
     * @return value of WebElement's indetified By "value" attribute
     */
    public String getValueAttributeLocatebBy(By by) {
        return waitForElementLocatedBy(by).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getValueAttributeLocatebByAndRemoveFormatting(By by) {
        return waitForElementLocatedBy(by).getAttribute(VALUE_ATTRIBUTE).replace(" ", "").replace(".", "").replace(",", "");
    }

    /**
     * @param webElement
     * @return state of WebLement radio button
     */
    public boolean isRadioChecked(WebElement webElement) {
        if (waitForElement(webElement).getAttribute("checked") == null) {
            return false;
        }
        return waitForElement(webElement).getAttribute("checked").equals("true");
    }

    /**
     * @param webElement
     * @return activity state of a WebElement radio button
     */
    public boolean isRadioActive(WebElement webElement) {
        return waitForElement(webElement).getAttribute("disabled").equals("true");
    }

    public boolean isInputActive(WebElement webElement) {
        if (waitForElement(webElement).getAttribute("disabled") == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return source of current page
     */
    public String getPageSource() {
        return getDriver().getPageSource();
    }

    /**
     * Switches to specified frame on the page.
     *
     * @param frame
     */
    public void switchToFrame(By frame) {
        getDriver().switchTo().defaultContent();
        getDriver().switchTo().frame(getDriver().findElement(frame));
    }

    public void switchToWindow(String windowHandle) {
        Set<String> windows = getDriver().getWindowHandles();

        for (String window : windows) {
            getDriver().switchTo().window(window);
            if (getDriver().getWindowHandle().equals(windowHandle)) {
                return;
            }
        }
    }

    public String getCurrentWindowHandle() {
        return getDriver().getWindowHandle();
    }

    /**
     * Clicks specified WebElement - before clicking waits for 10 seconds for WebElement's presence.
     *
     * @param element
     */
    public void clickElement(WebElement element) {
        waitForElement(element).click();
    }

    /**
     * Clicks specified WebElement - before clicking waits for 30 seconds for WebElement's presence.
     *
     * @param element
     */
    public void clickElementWait30Seconds(WebElement element) {
        waitForElement30Seconds(element).click();
    }

    /**
     * Clicks element specified by xpath node name and contained text.
     *
     * @param xpathNode
     * @param text
     */
    public void clickElementLocatedByText(String xpathNode, String text) {
//    WebElement element1 = getDriver().findElement(By.xpath("//" + xpathNode + "[text()='" + text + "']"));
//    scrollIntoView(element1);
        waitForElement(getDriver().findElement(By.xpath("//" + xpathNode + "[text()=' " + text + " ']"))).click();
    }

    public void doubleClickElementLocatedByText(String xpathNode, String text) {

        doubleClickElement(getDriver().findElement(By.xpath("//" + xpathNode + "[text()='" + text + "']")));
    }

    /**
     * @param path
     * @return canonical path to a file
     * If the file does not exist, null is returned
     */
    public String getFilePath(String path) {
        try {
            return new java.io.File(".").getCanonicalPath() + path;
        } catch (IOException e) {
            return null;
        }
    }

    // ------------------------Presence verification

    /**
     * @param by
     * @return true if element specified by a locator is NOT present on the page
     */
    public boolean isElementNotPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 1);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * //     * @param by
     *
     * @return true if WebElement is NOT present on the page
     */
    public boolean isElementNotPresent(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 1);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    /**
     * @param by
     * @return true if element specified by a locator was hided
     */
    public boolean wasElementHided(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * @param by
     * @return true if element specified by a locator is present on the page
     */
    public boolean isElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * //     * @param by
     *
     * @return true if WebElement is present on the page
     */
    public boolean isElementPresent(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
            } catch (StaleElementReferenceException e) {
                try {
                    Thread.sleep(WAIT_HALF_SECOND);
                } catch (InterruptedException e1) {
                    // skip this exception
                }
                wait.until(ExpectedConditions.visibilityOf(element));
            }
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * //     * @param by
     *
     * @return true if specified button is present AND NOT active
     */
    public boolean isButtonPresentAndInactive(By button) {
        waitForElementLocatedBy(button).isDisplayed();
        try {
            new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(button)));
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    /**
     * //     * @param by
     *
     * @return true if specified button is present AND active
     */
    public boolean isButtonPresentAndActive(By button) {
        try {
            waitForActiveButton(button);
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    // ------------------------Drop-down list handling

    /**
     * @param dropdownList
     * @return false when specified dropdown list is NOT active
     */
    public boolean isDropdownListActive(By dropdownList) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropdownList));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * @param dropdownList
     * @return false when specified dropdown list is active
     */
    public boolean isDropdownListNotActive(By dropdownList) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(dropdownList)));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * Sets value defined by an Enum value on specified select list.
     *
     * @param dropdownElement
     * @param value
     */
    @SuppressWarnings("rawtypes")
    protected void setValueOnDropdownField(WebElement dropdownElement, Enum value) {
        new Select(dropdownElement).selectByVisibleText(value.toString());
    }

    /**
     * Sets value defined by a String value on specified select list.
     *
     * @param dropdownElement
     * @param value
     */
    protected void setValueOnDropdownField(WebElement dropdownElement, String value) {
        new Select(dropdownElement).selectByVisibleText(value);
    }

    /**
     * Sets value on dropdown list based on index.
     *
     * @param dropdownElement
     * @param i
     */
    protected void setValueOnDropdownFieldByIndex(WebElement dropdownElement, int i) {
        new Select(dropdownElement).selectByIndex(i);
    }

    /**
     * @param dropdown
     * @return current value set on specified select list
     */
    protected String getDropdownCurrentValue(WebElement dropdown) {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    /**
     * @param dropdown
     * @return true if there is only one element available on dropdown
     */
    protected boolean isOnlyOneElementAvailableOnDropdown(WebElement dropdown) {
        try {
            new Select(dropdown).selectByIndex(1);
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    /**
     * Sets random value on specifies select list.
     *
     * @param dropdownElement
     * @param blankFirstValue
     * @return text from selected value
     */
    protected String setRandomValueOnDropdownField(WebElement dropdownElement, boolean blankFirstValue) {
        Select select = new Select(dropdownElement);
        List<WebElement> listedOptions = select.getOptions();
        int index = (int) (Math.random() * listedOptions.size());
        index = adjustIndex(blankFirstValue, index);
        listedOptions.get(index).click();
        return listedOptions.get(index).getText();
    }

    // ------------------------Select-box handling

    /**
     * @param selectbox
     * @return all options from a select list defined by a locator
     */
    public String getAllSelectboxOptions(By selectbox) {
        StringBuffer allSelectboxOptionsBuffer = new StringBuffer();
        List<WebElement> selectOptions = getDriver().findElements(selectbox);
        for (WebElement option : selectOptions) {
            allSelectboxOptionsBuffer.append(option.getText()).append(" ");
        }
        return allSelectboxOptionsBuffer.toString();
    }

    // ------------------------Checkbox handling

    /**
     * Sets expected value on a checkbox defined by a locator.
     *
     * @param checkbox
     * @param select
     */
    public void setCheckbox(By checkbox, boolean select) {
        waitForWaitModalNotPresent();
        int retry = CHECKBOX_SELECTION_RETRY_COUNT;
        if (select) {
            if (!isCheckboxSelected(checkbox)) {
                while (retry > 0) {

                    waitForElementLocatedBy(checkbox).click();
                    try {
                        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
                        wait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, true));
                        break;
                    } catch (TimeoutException e) {
                        retry--;
                    }

                }
            }
        } else {
            if (isCheckboxSelected(checkbox)) {
                while (retry > 0) {
                    waitForElementLocatedBy(checkbox).click();
                    try {
                        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
                        wait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, false));
                        break;
                    } catch (TimeoutException e) {
                        retry--;
                    }
                }
            }
        }
        waitForWaitModalNotPresent();
    }


    /**
     * Unsets a checkbox specified by a locator and waits for alert presence.
     *
     * @param checkbox
     */
    public void unsetCheckboxAndWaitForAlert(By checkbox) {
        boolean isAlertPresent = false;
        if (isCheckboxSelected(checkbox)) {
            while (!isAlertPresent) {
                waitForElementLocatedBy(checkbox).click();
                isAlertPresent = true;
                try {
                    WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
                    wait.until(ExpectedConditions.alertIsPresent());
                } catch (NoAlertPresentException e) {
                    isAlertPresent = false;
                }
            }
        }
    }

    /**
     * @param checkbox
     * @return true if the specified checkbox is selected
     */
    public boolean isCheckboxSelected(final By checkbox) {
        return waitForElementLocatedBy(checkbox).isSelected();
    }

    /**
     * @param checkbox
     * @return true if the specified checkbox is NOT selected
     */
    public boolean isCheckboxUnselected(final By checkbox) {
        return !(getDriver().findElement(checkbox).isSelected());
    }

    // ------------------------waitFor[...] methods

    /**
     * @param radio
     * @return true if specified radio is active
     */
    public boolean isRadioActive(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
            wait.until(ExpectedConditions.elementToBeClickable(radio));
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    /**
     * @param radio
     * @return true if specified radio is selected
     */
    public boolean isRadioSelected(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
            wait.until(ExpectedConditions.elementSelectionStateToBe(radio, true));
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    /**
     * @param radio
     * @return true if specified radio is not selected
     */
    public boolean isRadioNotSelected(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
            wait.until(ExpectedConditions.elementSelectionStateToBe(radio, false));
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    /**
     * @param radio
     * @return true if specified radio is not active
     */
    public boolean isRadioNotActive(By radio) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
        return wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(radio)));
    }

    /**
     * @param radio
     * @return clickable element
     */
    public WebElement waitForClickableRadio(By radio) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS);
        return wait.until(ExpectedConditions.elementToBeClickable(radio));
    }

    /**
     * @param element
     * @return visible element
     * Waits for 10 seconds
     */
    public WebElement waitForElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS);
        try {
            try {
                return wait.until(ExpectedConditions.visibilityOf(element));
            } catch (WebDriverException e) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                } catch (InterruptedException e1) {
                    return wait.until(ExpectedConditions.visibilityOf(element));
                }
                return wait.until(ExpectedConditions.visibilityOf(element));
            }
        } catch (StaleElementReferenceException e) {
            try {
                Thread.sleep(SLEEP_INTERVAL);
                return wait.until(ExpectedConditions.visibilityOf(element));
            } catch (Exception e1) {
                return wait.until(ExpectedConditions.visibilityOf(element));
            }
        }
    }


    /**
     * @param element
     * @return visible element
     * Waits for 2 seconds
     */
    public WebElement waitForElementLocatedByFor2Sec(By element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_2_SECONDS);
        try {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            } catch (WebDriverException e) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                } catch (InterruptedException e1) {
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
                }
                return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            }
        } catch (StaleElementReferenceException e) {
            try {
                Thread.sleep(SLEEP_INTERVAL);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            } catch (InterruptedException e1) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            }
        }
    }

    /**
     * @param element
     * @return visible WebElement
     * Waits for 30 second
     */
    public WebElement waitForElement30Seconds(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS);
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException e) {
            return wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    /**
     * @param element
     * @return boolean value
     */
    public boolean checkElementHasText(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS);
        return wait.until(ExpectedConditions.textToBePresentInElement(element,text));
    }





    /**
     * //     * @param element
     *
     * @return visible WebElement located by a locator
     * Waits for 10 second
     */
    public WebElement waitForElementLocatedBy(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_10_SECONDS);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (StaleElementReferenceException e) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
    }

    /**
     * //     * @param element
     *
     * @return visible WebElement located by a locator
     * Waits for 30 second
     */
    public WebElement waitForElementLocatedByFor30Seconds(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (StaleElementReferenceException e) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
    }

    /**
     * Waits for an element located by a locator.
     *
     * @param by
     */
    public void waitForElementPresent(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_10_SECONDS);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
    }

    /**
     * Waits 10 seconds until element located by a locator is hided.
     *
     * @param by
     */
    public void waitForElementNotPresent(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_10_SECONDS);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        }
    }

    /**
     * Waits 40 seconds until element located by a locator is hided.
     *
     * @param by
     */
    public void waitForElementNotPresentFor40Seconds(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_40_SECONDS);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        }
    }

    /**
     * Waits for text to be present in specified element for 10 seconds.
     *
     * @param by
     * @param expectedText
     */
    public void waitForTextToBePresentInWebElement(By by, String expectedText) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_10_SECONDS);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(by, expectedText));
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.textToBePresentInElement(by, expectedText));
        }
    }

    /**
     * Waits for text to be present in specified element for 30 seconds.
     *
     * @param by
     * @param expectedText
     */
    public void waitForTextToBePresentInWebElementFor30Sec(By by, String expectedText) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(by, expectedText));
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.textToBePresentInElement(by, expectedText));
        }
    }

    /**
     * Waits for active button.
     *
     * @param by
     */
    public void waitForActiveButton(By by) {
        waitForWaitModalNotPresent();
        new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS).until(ExpectedConditions.elementToBeClickable(by));
    }


    /**
     * Waits for not active button.
     *
     * @param by
     */
    public void waitForNotClickableElement(By by) {
        new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by)));
    }

    /**
     * Waits for not active element.
     *
     * @param by
     */
    public void waitForNotActiveElement(By by) {
        new WebDriverWait(getDriver(), TIME_OUT_5_SECONDS).until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(by)));
    }

    /**
     * @param webElementList
     * @param numberElementsToWaitFor
     * @return true if the WebElement list size is greater than the @param numberElementsToWaitFor
     */
    public boolean waitUntilElementsAreLoaded(final List<WebElement> webElementList, final int numberElementsToWaitFor) {
        return new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS).until(new WaitForAllWebElements(webElementList, numberElementsToWaitFor));
    }

    public static class WaitForAllWebElements implements ExpectedCondition<Boolean> {
        private List<WebElement> webElementList;
        private int numberElementsToWaitFor;

        public WaitForAllWebElements(List<WebElement> webElementList, int numberElementsToWaitFor) {
            this.webElementList = webElementList;
            this.numberElementsToWaitFor = numberElementsToWaitFor;
        }

        @Override
        public Boolean apply(WebDriver webDriver) {
            return webElementList.size() > numberElementsToWaitFor;
        }
    }

    /**
     * @param webElementList
     * @return true if the specified WebElement list is not empty
     */
    public Boolean waitUntilElementsArePresent(final List<WebElement> webElementList) {
        return new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS).until(new WaitTillAllWebElementsAreEmpty(webElementList));
    }

    private static class WaitTillAllWebElementsAreEmpty implements ExpectedCondition<Boolean> {
        private List<WebElement> webElementList;

        public WaitTillAllWebElementsAreEmpty(List<WebElement> webElementList) {
            this.webElementList = webElementList;
        }

        @Override
        public Boolean apply(WebDriver webDriver) {
            return !webElementList.isEmpty();
        }
    }

    /**
     * @param webElement
     * @return visible WebElement
     * Waits for 30 seconds
     */
    public WebElement waitUntilIsVisible(final WebElement webElement) {
        return new WebDriverWait(getDriver(), TIME_OUT_30_SECONDS).until(new WaitTillWebElementIsVisible(webElement));
    }

    private static class WaitTillWebElementIsVisible implements ExpectedCondition<WebElement> {
        private WebElement webElement;

        public WaitTillWebElementIsVisible(WebElement webElement) {
            this.webElement = webElement;
        }

        @Override
        public WebElement apply(WebDriver driver) {
            return webElement;
        }
    }

    public void waitForWaitModalNotPresent() {
        try {
            waitForElementNotPresentFor40Seconds(By.id("waitModal_content"));
        } catch (Exception e) {
        }
    }

    // ------------------------close method

    /**
     * Method closing web browser.
     */
    public void close() {
//    	Utils.closeDriver();
    }

    //-----------------------Handling Floating button
    protected void clickUsingJS(By byScreenedButton) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("arguments[0].click();", byScreenedButton);
            waitForWaitModalNotPresent();
        } catch (WebDriverException e) {
            TestBase.logger.info("Error clicking: " + e);
        }
    }

    protected void clickUsingJS(WebElement button) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("arguments[0].click();", button);
            waitForWaitModalNotPresent();
        } catch (WebDriverException e) {
            TestBase.logger.info("Error clicking: " + e);
        }
    }


    /* * scrollIntoView method Scrolls a page based on its presence and visibility
     * @return
     * @throws Exception
     */
    protected void scrollIntoView(WebElement element) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("arguments[0].scrollIntoView(true);", element);

        } catch (WebDriverException e) {
            TestBase.logger.info("Error scrolling: " + e);
        }
    }

    protected void scrollIntoView(By element) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("arguments[0].scrollIntoView(true);", element);

        } catch (WebDriverException e) {
            TestBase.logger.info("Error scrolling: " + e);
        }
    }


    protected void scrollDownUsingJS() {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("window.scrollBy(0,250);", "");

        } catch (WebDriverException e) {
//            logger.info("Error scrolling: " + e);
        }
    }


    protected void scrollUpUsingJS() {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("window.scrollBy(0,-470);", "");

        } catch (WebDriverException e) {
//            logger.info("Error scrolling: " + e);
        }
    }


    public void hideFloatingButton(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("arguments[0].style.visibility='hidden'", element);
        } catch (WebDriverException e) {
            TestBase.logger.info("Error scrolling: " + e);
        }
    }

    public void showFloatingButton(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            waitForWaitModalNotPresent();
            js.executeScript("arguments[0].style.visibility='visible'", element);
        } catch (WebDriverException e) {
            TestBase.logger.info("Error scrolling: " + e);
        }
    }

    // under development
    public void validateCellDataExists(String tableXpath, String td_xpath, String col_xpath) {
        boolean bstatus = false;
        try {
            //  WebElement table_el

        } catch (Exception e) {

        }
    }


}
