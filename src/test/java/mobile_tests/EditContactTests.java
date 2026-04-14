package mobile_tests;

import dto.Contact;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.*;
import utils.Direction;

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
        contactListScreen.editFirstContact(contactUpdate);
        softAssert.assertTrue(contactListScreen.validateTextInMessageContactWasUpdated("Contact was updated!", 5));
        softAssert.assertTrue(contactListScreen.isUpdatedContactPresentInList(contactUpdate));
        softAssert.assertAll();
    }

    @Test
    public void editFirstContactNegativeTest_EmptyFirstName(){
        Contact contactUpdate = positiveContact();
        contactUpdate.setName("");
        contactListScreen.editFirstContact(contactUpdate);
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must not be blank", 5));
    }
}
