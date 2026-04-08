package screens;

import dto.Contact;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;

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

    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was updated!']")
    WebElement messageContactWasUpdated;

    @AndroidFindBy(xpath = "(//android.widget.LinearLayout[@resource-id='com.sheygam.contactapp:id/rowContainer'])[1]")
    WebElement firstContact;

    @AndroidFindBy(id="com.sheygam.contactapp:id/rowName")
    WebElement contactName;

    @AndroidFindBy(id="com.sheygam.contactapp:id/rowPhone")
    WebElement contactPhone;

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

    public boolean validateTextInMessageContactWasUpdated(String text, int time){
       return isTextInElementPresent(messageContactWasUpdated, text, time);
    }

    public void swipeFirstContactLeft(){
        Rectangle rect = firstContact.getRect();
        int centerY = rect.y + (rect.height / 2);
        int startX = rect.x + (int) (rect.width * 0.9);
        int endX = rect.x + (int) (rect.width * 0.5);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, centerY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, centerY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    public boolean isUpdatedContactPresentInList(Contact contact){
        return isTextInElementPresent(contactName, contact.getName()+ " " + contact.getLastName(), 5)
                && isTextInElementPresent(contactPhone, contact.getPhone(), 5);
    }
}
