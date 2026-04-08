package mobile_tests;

import data_providers.ContactDataProvider;
import data_providers.UserDataProvider;
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
    //===========================HW21===========================//

    //response 1

    @Test(dataProvider = "dataProviderFromFile_Contact_EmptyName", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_EmptyName(Contact contact){
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must not be blank", 5), "validate message if name is empty");
    }

    @Test(dataProvider = "dataProviderFromFile_Contact_EmptyLastName", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_EmptyLastName(Contact contact){
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must not be blank", 5), "validate message if last name is empty");
    }

    @Test(dataProvider = "dataProviderFromFile_Contact_EmptyAddress", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_EmptyAddress(Contact contact){
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must not be blank", 5), "validate message if address is empty");
    }

    // response 2

    @Test(dataProvider = "dataProviderFromFile_Contact_WrongPhone", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_WrongPhone(Contact contact){
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("Phone number must contain only digits!", 5), "validate message if address is empty");
    }

    //response 3

    @Test(dataProvider = "dataProviderFromFile_Contact_WrongEmail", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_WrongEmail(Contact contact){
        addNewContactScreen.typeAddContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must be a well-formed email address", 5), "validate message if address is empty");
    }

}
