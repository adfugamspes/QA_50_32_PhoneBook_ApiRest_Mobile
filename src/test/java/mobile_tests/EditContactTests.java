package mobile_tests;

import dto.Contact;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.EditContactScreen;
import screens.LoginRegistrationScreen;

import static utils.UserFactory.*;
import static utils.ContactFactory.*;

public class EditContactTests extends TestBase{

    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    EditContactScreen editContactScreen;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void login(){
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = positiveUserLogin();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
    }

    @Test
    public void editFirstContactPositiveTest(){
        Contact contactUpdate = positiveContact();
        contactListScreen.swipeFirstContactLeft();
        editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditContactForm(contactUpdate);
        editContactScreen.clickBtnUpdateContact();
        softAssert.assertTrue(contactListScreen.validateTextInMessageContactWasUpdated("Contact was updated!", 5));
        softAssert.assertTrue(contactListScreen.isUpdatedContactPresentInList(contactUpdate));
    }
}
