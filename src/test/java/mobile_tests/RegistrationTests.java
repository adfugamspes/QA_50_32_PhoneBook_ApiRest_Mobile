package mobile_tests;

import data_providers.ContactDataProvider;
import data_providers.UserDataProvider;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;
import screens.SplashScreen;

import static utils.PropertiesReader.getProperty;
import static utils.UserFactory.*;
public class RegistrationTests extends TestBase{

    LoginRegistrationScreen loginRegistrationScreen;

    @BeforeMethod
    public void openAuthScreen(){
        new SplashScreen(driver);
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test
    public void registrationPositiveTest(){
        User user = positiveUser();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ContactListScreen(driver).validateTextInContactListScreenAfterRegistrationPresent
                ("No Contacts. Add One more!", 10));
    }

    // response 1
    @Test
    public void registrationNegativeTest_EmptyEmail(){
        User user = positiveUser();
        user.setUsername("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must not be blank", 5));
    }

    // response 2
    @Test(dataProvider = "dataProviderFromFile_UserRegistration", dataProviderClass = UserDataProvider.class)
    public void registrationNegativeTest_EmptyOrSpaceFields(User user){
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertNotNull(driver.getSessionId(), "validate app crush");
    }

    // response 3
    @Test(dataProvider = "dataProviderFromFile_UserRegistration2", dataProviderClass = UserDataProvider.class)
    public void registrationNegativeTest_WrongEmail(User user){
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must be a well-formed email address", 5));
    }

    // response 4
    @Test(dataProvider = "dataProviderFromFile_UserRegistration3", dataProviderClass = UserDataProvider.class)
    public void registrationNegativeTest_WrongPassword(User user){
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("password= At least 8 characters;", 5));
    }

    // response 5
    @Test
    public void registrationNegativeTest_UserAlreadyExists(){
        User user = new User(getProperty("base.properties", "login"), getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("User already exists", 5));
    }

    //=================================CW==================================//

    // эти тесты не будут работать, даже с flaky test, т.к. при запуске тестов подряд выдаются разные краши
    // при первом краше выдается сообщение "Open app again"
    // при втором краше - "Close app"
    // при третьем - месседж закрывается очень быстро, сообщение не видно

//    @Test
//    public void registrationNegativeTest_EmptyEmailSpace(){
//        User user = positiveUser();
//        user.setUsername(" ");
//        loginRegistrationScreen.typeLoginRegistrationForm(user);
//        loginRegistrationScreen.clickBtnRegistration();
//        Assert.assertTrue(new ErrorScreen(driver).validateCrashScreen("Open app again", 5));
//    }
//
//    @Test
//    public void registrationNegativeTest_EmptyFields(){
//        User user = new User("", "");
//        loginRegistrationScreen.typeLoginRegistrationForm(user);
//        loginRegistrationScreen.clickBtnRegistration();
//        Assert.assertTrue(new ErrorScreen(driver).isAppStoppedDisplayed());
//    }
}
