package mobile_tests;

import dto.Contact;
import dto.ContactsDto;
import dto.TokenDto;
import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import manager.ContactController;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;
import utils.BaseApi;
import static utils.ContactFactory.*;

import java.time.Duration;

import static utils.UserFactory.positiveUser;
import static utils.UserFactory.positiveUserLogin;

public class DeleteContactTests extends TestBase {
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    TokenDto tokenDto;
    ContactsDto contactsDtoBeforeDelete;
    ContactsDto contactsDtoAfterDelete;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(onlyForGroups = {"withLogin"})
    public void login() {
        User user = positiveUserLogin();
        tokenDto = AuthenticationController.requestRegLogin(user, BaseApi.LOGIN_URL).as(TokenDto.class);
        Response response = ContactController.requestGetAllUserContacts(tokenDto.getToken());
        System.out.println(response.getStatusLine());
        if (response.getStatusCode() == 200) {
            contactsDtoBeforeDelete = response.as(ContactsDto.class);
        }
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
    }

    @Test(groups = "withLogin")
    public void deleteContactPositiveTest() {
        int sizeBeforeDelete = contactsDtoBeforeDelete.getContacts().size();
        contactListScreen.deleteContactMiddle();
        int sizeAfterDelete = ContactController.requestGetAllUserContacts(tokenDto.getToken())
                .as(ContactsDto.class).getContacts().size();
        System.out.println(sizeBeforeDelete + " - " + sizeAfterDelete);
        Assert.assertEquals(sizeAfterDelete, sizeBeforeDelete-1);
    }

    @Test(groups = "withLogin")
    public void deleteFirstContactPositiveTest() {
        int sizeBeforeDelete = contactsDtoBeforeDelete.getContacts().size();
        contactListScreen.deleteFirstContact_CW();
        int sizeAfterDelete = ContactController.requestGetAllUserContacts(tokenDto.getToken())
                .as(ContactsDto.class).getContacts().size();
        System.out.println(sizeBeforeDelete + " - " + sizeAfterDelete);
        Assert.assertEquals(sizeAfterDelete, sizeBeforeDelete-1);
    }

    @Test
    public void deleteContactTest_NewUserOnlyOneContact(){
        User user = positiveUser();
        tokenDto = AuthenticationController.requestRegLogin(user, BaseApi.REGISTRATION_URL).as(TokenDto.class);
        Contact contact = positiveContact();
        Response response = ContactController.requestAddNewContact(contact,tokenDto.getToken());
        softAssert.assertEquals(response.getStatusCode(), 200, "add new contact validation");
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
        contactListScreen.deleteFirstContact_CW();
        int sizeAfterDelete = ContactController.
                requestGetAllUserContacts(tokenDto.getToken()).as(ContactsDto.class).getContacts().size();
        softAssert.assertEquals(sizeAfterDelete, 0, "empty contact list validation");
        softAssert.assertTrue(contactListScreen.isContactListEmpty(), "empty contact list screen validation");
        softAssert.assertAll();
    }
}
