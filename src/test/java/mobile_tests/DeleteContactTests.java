package mobile_tests;

import dto.User;
import org.testng.annotations.BeforeMethod;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;

import static utils.UserFactory.positiveUserLogin;

public class DeleteContactTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;

    @BeforeMethod
    public void login(){
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = positiveUserLogin();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
        contactListScreen.clickBtnPlus();
    }
}
