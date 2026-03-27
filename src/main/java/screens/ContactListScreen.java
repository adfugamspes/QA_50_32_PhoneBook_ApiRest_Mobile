package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ContactListScreen extends BaseScreen{

    public ContactListScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.sheygam.contactapp:id/emptyTxt")
    WebElement noContacts;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Contact list']")
    WebElement contactList;

    @AndroidFindBy(accessibility = "add")
    WebElement btnAddContact;

    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was added!']")
    WebElement messageContactWasAdded;

    public boolean validateTextInContactListScreenAfterRegistrationPresent(String text, int time){
        return isTextInElementPresent(noContacts, text, time);
    }

    public boolean validateTextInContactListPresent(String text, int time){
        return isTextInElementPresent(contactList, text, time);
    }

    public boolean isBtnPlusPresent(){
        return isElementPresent(btnAddContact, 5);
    }

    public void clickBtnPlus(){
        btnAddContact.click();
    }

    public boolean validateTextInMessageContactWasAddedPresent(String text, int time){
        return isTextInElementPresent(messageContactWasAdded, text, time);
    }
}
