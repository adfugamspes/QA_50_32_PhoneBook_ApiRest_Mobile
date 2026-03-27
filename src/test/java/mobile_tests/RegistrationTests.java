package mobile_tests;

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

    @Test
    public void registrationNegativeTest_EmptyEmail(){
        User user = positiveUser();
        user.setUsername("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must not be blank", 5));
    }

    // по хорошему здесь не должно быть валидации крашей, все краши - это баги
    // по логике здесь нужна валидация сообщений типа "поле не должно быть пустым", как в логине

    @Test
    public void registrationNegativeTest_EmptyPassword(){
        User user = positiveUser();
        user.setPassword("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertNotNull(driver.getSessionId(), "validate app crush");
    }

    @Test
    public void registrationNegativeTest_EmptyEmailAndPassword(){
        User user = positiveUser();
        user.setUsername("");
        user.setPassword("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertNotNull(driver.getSessionId(), "validate app crush");
    }

    @Test
    public void registrationNegativeTest_EmptyEmailOnlySpace(){
        User user = positiveUser();
        user.setUsername(" ");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertNotNull(driver.getSessionId(), "validate app crush");
    }

    @Test
    public void registrationNegativeTest_EmptyPasswordOnlySpace(){
        User user = positiveUser();
        user.setPassword(" ");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertNotNull(driver.getSessionId(), "validate app crush");
    }

    @Test
    public void registrationNegativeTest_EmailWOAtSymbol(){
        User user = positiveUser();
        user.setUsername("testexample123gmail.com");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must be a well-formed email address", 5));
    }

    @Test
    public void registrationNegativeTest_Email2AtSymbol(){
        User user = positiveUser();
        user.setUsername("testexample@@123gmail.com");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must be a well-formed email address", 5));
    }

    @Test
    public void registrationNegativeTest_EmailNoLettersAfterAtSign(){
        User user = positiveUser();
        user.setUsername("testexample@@");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must be a well-formed email address", 5));
    }

    @Test
    public void registrationNegativeTest_EmailNoLettersBeforeAtSign(){
        User user = positiveUser();
        user.setUsername("@123gmail.com");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must be a well-formed email address", 5));
    }

    @Test
    public void registrationNegativeTest_EmailOnlyCyrillicLetters_BUG(){
        User user = positiveUser();
        user.setUsername("тестоваяпочта1@гмейл.ком");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("username=must be a well-formed email address", 5));
    }

    @Test
    public void registrationNegativeTest_PasswordNoUppercase(){
        User user = positiveUser();
        user.setPassword("password!123");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("password= At least 8 characters;", 5));
    }

    @Test
    public void registrationNegativeTest_PasswordNoLowercase(){
        User user = positiveUser();
        user.setPassword("PASSWORD!123");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("password= At least 8 characters;", 5));
    }

    @Test
    public void registrationNegativeTest_PasswordNoNumbers(){
        User user = positiveUser();
        user.setPassword("Password!test");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("password= At least 8 characters;", 5));
    }

    @Test
    public void registrationNegativeTest_Password7Symbols(){
        User user = positiveUser();
        user.setPassword("Pas!123");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("password= At least 8 characters;", 5));
    }

    @Test
    public void registrationNegativeTest_Password16Symbols_BUG(){
        User user = positiveUser();
        user.setPassword("Password!1234567");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("password= At least 8 characters;", 5));
    }

    @Test
    public void registrationNegativeTest_UserAlreadyExists(){
        User user = positiveUserLogin();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("User already exists", 5));
    }

//=================================CW==================================//
    @Test
    public void registrationNegativeTest_EmptyEmailSpace(){
        User user = positiveUser();
        user.setUsername(" ");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateCrashScreen("Open app again", 5));
    }

    @Test
    public void registrationNegativeTest_EmptyFields(){
        User user = new User("", "");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).isAppStoppedDisplayed());
    }

    //==============================CW=============================//

    @Test
    public void registrationNegativeTest_UserAlreadyExists_CW(){
        User user = new User(getProperty("base.properties", "login"), getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("User already exists", 5));
    }
}
