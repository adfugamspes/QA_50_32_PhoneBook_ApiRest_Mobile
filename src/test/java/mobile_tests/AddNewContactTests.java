package mobile_tests;

import dto.Contact;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;
import static utils.PropertiesReader.*;
import static utils.UserFactory.*;
import static utils.ContactFactory.*;

public class AddNewContactTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    AddNewContactScreen addNewContactScreen;

    @BeforeMethod
    public void login(){
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = positiveUserLogin();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
        contactListScreen.clickBtnPlus();
        addNewContactScreen = new AddNewContactScreen(driver);
    }

    @Test
    public void addNewContactPositiveTest(){
        Contact contact = positiveContact();
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(contactListScreen.validateTextInMessageContactWasAddedPresent("Contact was added!", 5));
    }

    @Test
    public void addNewContactNegativeTest_WrongPhoneLength(){
        Contact contact = positiveContact();
        contact.setPhone("123456789");
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("Phone number must contain", 5));
    }

    @Test
    public void addNewContactNegativeTest_EmptyName(){
        Contact contact = positiveContact();
        contact.setName("");
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must not be blank", 5));
    }

    @Test
    public void addNewContactNegativeTest_EmptyLastName(){
        Contact contact = positiveContact();
        contact.setLastName("");
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must not be blank", 5));
    }

}
