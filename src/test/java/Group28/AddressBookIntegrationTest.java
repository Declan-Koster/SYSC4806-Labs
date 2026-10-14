package Group28;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.test.web.servlet.client.RestTestClient;

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
        // Create an address book
        String addressBookUri = restTestClient.post()
                .uri("/addressBooks")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseHeaders()
                .getLocation()
                .toString();

        // Create a buddy
        String buddyUri = restTestClient.post()
                .uri("/buddyInfoes")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("{\"name\":\"Alex\",\"number\":\"555-6543\"}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseHeaders()
                .getLocation()
                .toString();

        // Associate the buddy with the address book
        restTestClient.post()
                .uri(addressBookUri + "/friends")
                .contentType(org.springframework.http.MediaType.parseMediaType("text/uri-list"))
                .body(buddyUri)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void testGetFriendsFromAddressBook() {
        // Create an address book
        String addressBookUri = restTestClient.post()
                .uri("/addressBooks")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseHeaders()
                .getLocation()
                .toString();

        // Create a buddy
        String buddyUri = restTestClient.post()
                .uri("/buddyInfoes")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("{\"name\":\"Alex\",\"number\":\"555-6543\"}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseHeaders()
                .getLocation()
                .toString();

        // Associate the buddy with the address book
        restTestClient.post()
                .uri(addressBookUri + "/friends")
                .contentType(org.springframework.http.MediaType.parseMediaType("text/uri-list"))
                .body(buddyUri)
                .exchange()
                .expectStatus().is2xxSuccessful();

        // Retrieve the friends and verify the response
        restTestClient.get()
                .uri(addressBookUri + "/friends")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$._embedded.buddyInfoes[0].name")
                .isEqualTo("Alex");
    }
}