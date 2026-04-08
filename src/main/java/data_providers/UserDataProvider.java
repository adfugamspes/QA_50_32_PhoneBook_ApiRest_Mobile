package data_providers;

import dto.User;
import net.datafaker.Faker;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static utils.UserFactory.*;

public class UserDataProvider {
    @DataProvider
    public Iterator<User> dataProviderFromFile_UserRegistration_EmptyFields() {
        List<User> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_user_registration_empty_fields.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",", -1);
                list.add(User.builder().username(splitArray[0])
                        .password(splitArray[1]).build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<User> dataProviderFromFile_UserRegistration_WrongEmail() {
        User user = positiveUser();
        List<User> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_user_registration_wrong_email.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",", -1);
                list.add(User.builder().username(splitArray[0])
                        .password(user.getPassword()).build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<User> dataProviderFromFile_UserRegistration_WrongPassword() {
        List<User> list = new ArrayList<>();
        User user = positiveUser();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data.csv/data_provider_user_registration_wrong_password.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",", -1);
                list.add(User.builder().username(user.getUsername())
                        .password(splitArray[1]).build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }
}
