package com.project.project.tests;

import com.aventstack.extentreports.Status;
import com.project.project.components.*;
import com.project.project.pages.InternetHomePage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InternetTestSuiteDataProviderExampleLoginTest extends TestBase {

    private static InternetHomePage homePage;

    @DataProviderArguments(file = "InternetLoginValues.xlsx")
    @Xray(test = "INTERNET-75", labels = "Label2")
    @Test(groups = {"login","regression"}, dataProvider = "getDataFromFile", dataProviderClass = TestDataProvider.class)
//    @Test(groups = {"login","regression"})
    public void successfulLoginArrayDataProviderTest(String name, String password) {
        //given:
        testNameParameter.set(name+","+password);
        //when:
        homePage = loginPage.get().login(name, password);
        //then:
        assertThat(homePage.isLoaded(name)).isTrue();
    }


    @ExcelDataProvider(fileName = "LoginWithIncorrectPasswords.xlsx", tab = "Sheet2")
    @Test(groups = {"login","regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
//    @Test(groups = {"login","regression"})
    public void unsuccessLogiExcelDataProviderTabTest(String name, String password, String col3, String col4) {
        //given:
        testNameParameter.set(col3+","+col4);
        //when:
        homePage = loginPage.get().login(col3, col4);
        assertThat(homePage.isLoaded(name)).isFalse();
    }
}
