package com.project.project.tests;

import com.project.project.components.TestBase;
import org.testng.annotations.Test;

/**
 * Created by a049473 on 2017-10-24.
 */
public class VisualTestingTest extends TestBase {


    @Test(groups = "visual")
    public void loginUsingCorrectValues() {
        //when: "try to logon to system using correct password"
        String un = "A049473";
        String pw = "Testy123";
        homePage = loginPage.get().login(un, pw);
        //then:
        eyes.checkWindow("systemHomePage");
    }

}
