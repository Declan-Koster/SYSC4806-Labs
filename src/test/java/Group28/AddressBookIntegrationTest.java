package Group28;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureRestTestClient
class AddressBookIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void testCreateAddressBook() {
        restTestClient.post()
                .uri("/addressBooks")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void testCreateBuddyInfo() {
        restTestClient.post()
                .uri("/buddyInfoes")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("{\"name\":\"Alex\",\"number\":\"555-6543\"}")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void testAddBuddyToAddressBook() {
        // Create an address book through the web form
        String addressBookUri = restTestClient.post()
                .uri("/addressBooks")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseHeaders()
                .getLocation()
                .toString();

        // Extract the address book ID from its URI
        String id = addressBookUri.substring(
                addressBookUri.lastIndexOf('/') + 1);

        // Submit the same form fields used by the webpage
        restTestClient.post()
                .uri("/addressBooks/" + id + "/friends")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("name=Alex&number=555-6543&address=Ottawa")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location(
                        "/addressBooks/" + id + "/view");

        // Verify that the buddy appears on the address book page
        restTestClient.get()
                .uri("/addressBooks/" + id + "/view")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(result -> {
                    String page = result.getResponseBody();
                    org.junit.jupiter.api.Assertions.assertTrue(
                            page.contains("Alex"));
                    org.junit.jupiter.api.Assertions.assertTrue(
                            page.contains("555-6543"));
                    org.junit.jupiter.api.Assertions.assertTrue(
                            page.contains("Ottawa"));
                });
    }

    @Test
    void testGetFriendsFromAddressBook() {
        // The view page should load successfully
        // Use an address book created through the existing REST API
        String addressBookUri = restTestClient.post()
                .uri("/addressBooks")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseHeaders()
                .getLocation()
                .toString();

        String id = addressBookUri.substring(
                addressBookUri.lastIndexOf('/') + 1);

        restTestClient.get()
                .uri("/addressBooks/" + id + "/view")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(result ->
                        org.junit.jupiter.api.Assertions.assertTrue(
                                result.getResponseBody()
                                        .contains("Current Buddies")));
        }
}