package mobile_tests;

import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;
import screens.SplashScreen;
import static utils.PropertiesReader.*;
import static utils.UserFactory.positiveUser;
import static utils.UserFactory.positiveUserLogin;

public class LoginTests extends TestBase {
    LoginRegistrationScreen loginRegistrationScreen;

    @BeforeMethod
    public void openAuthScreen() {
//        new SplashScreen(driver);
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test
    public void loginPositiveTest() {
        User user = positiveUserLogin();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ContactListScreen(driver)
                .validateTextInContactListPresent("Contact list", 10));
    }
    //=================================CW==============================//
    @Test
    public void loginPositiveTest_CW() {
        User user = new User(getProperty("base.properties", "login"), getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ContactListScreen(driver)
                .isBtnPlusPresent());
    }

    @Test
    public void loginNegativeTest_EmptyEmail() {
        User user = new User("", getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Login or Password incorrect", 5));
    }

    @Test
    public void loginNegativeTest_EmptyFields() {
        User user = new User("", "");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Login or Password incorrect", 5));
    }

    @Test
    public void loginNegativeTest_EmailOnlySpaces() {
        User user = new User(" ", getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Login or Password incorrect", 5));
    }


}
