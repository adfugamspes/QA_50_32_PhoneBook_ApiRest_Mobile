package data_providers;
import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static utils.ContactFactory.*;

public class ContactDataProvider {

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Contact_EmptyName() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_add_contact_empty_fields.csv"))){
            String line = bufferedReader.readLine();
            while (line!=null){
                String[] splitArray = line.split(",");
                list.add(Contact.builder().name(splitArray[0])
                        .lastName(contact.getLastName())
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Contact_EmptyLastName() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_add_contact_empty_fields.csv"))){
            String line = bufferedReader.readLine();
            while (line!=null){
                String[] splitArray = line.split(",");
                list.add(Contact.builder().name(contact.getName())
                        .lastName(splitArray[0])
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Contact_EmptyAddress() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_add_contact_empty_fields.csv"))){
            String line = bufferedReader.readLine();
            while (line!=null){
                String[] splitArray = line.split(",");
                list.add(Contact.builder().name(contact.getName())
                        .lastName(contact.getAddress())
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .address(splitArray[0])
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Contact_WrongPhone() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_wrong_phone.csv"))){
            String line = bufferedReader.readLine();
            while (line!=null){
                String[] splitArray = line.split(",");
                list.add(Contact.builder().name(contact.getName())
                        .lastName(contact.getAddress())
                        .email(contact.getEmail())
                        .phone(splitArray[0])
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Contact_WrongEmail() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_add_contact_wrong_email.csv"))){
            String line = bufferedReader.readLine();
            while (line!=null){
                String[] splitArray = line.split(",");
                list.add(Contact.builder().name(contact.getName())
                        .lastName(contact.getAddress())
                        .email(splitArray[0])
                        .phone(contact.getPhone())
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }
}
