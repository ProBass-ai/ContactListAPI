package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactListSteps {
    RequestSpecification httpRequest;
    Response response;
    String name;
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGQwZjkwYTRmMzVmMzAwMTMwM2M5ZjYiLCJpYXQiOjE2OTE0MTY4NDJ9.5tcBKDRuYmWzi-0rQuPScsZkKdZwbYA6I9N0ZISGZlo";

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/contacts";
        httpRequest = RestAssured.given();
    }

    @When("the user adds a new contact with {string} and surname {string}")
    public void theUserAddsANewContactWithAndSurname(String name, String surname) {
        httpRequest.header("Authorization", token);
        name = UUID.randomUUID().toString().substring(6);

        String json = "{\n" +
                "    \"firstName\": \"" + name +"\",\n" +
                "    \"lastName\": \"" + UUID.randomUUID().toString().substring(6) + "@fake.com" + "\",\n" +
                "    \"birthdate\": \"1970-01-01\",\n" +
                "    \"email\": \"jdoe@fake.com\",\n" +
                "    \"phone\": \"8005555555\",\n" +
                "    \"street1\": \"1 Main St.\",\n" +
                "    \"street2\": \"Apartment A\",\n" +
                "    \"city\": \"Anytown\",\n" +
                "    \"stateProvince\": \"KS\",\n" +
                "    \"postalCode\": \"12345\",\n" +
                "    \"country\": \"USA\"\n" +
                "}";
        httpRequest.body(json);
        response = httpRequest.post();
    }

    @Then("the user is in the list of contacts")
    public void theUserIsInTheListOfContacts() {
        httpRequest.header("Authorization", token);
        response = httpRequest.get();
        List<String> names = response.body().jsonPath().getList("$.firstName", String.class);
        assertTrue(names.contains(name));
    }
}
