package Group28;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookTest {

    @Test
    void testEmptyAddressBook() {
        AddressBook addressBook = new AddressBook();

        assertEquals(0, addressBook.numFriends());
    }

    @Test
    void testAddFriend() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo friend = new BuddyInfo("L", "123456");

        addressBook.addFriend(friend);

        assertEquals(1, addressBook.numFriends());
        assertTrue(addressBook.getFriends().contains(friend));
    }

    @Test
    void testRemoveFriend() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo friend = new BuddyInfo("L", "123456");

        addressBook.addFriend(friend);
        assertEquals(1, addressBook.numFriends());
        assertTrue(addressBook.getFriends().contains(friend));

        addressBook.removeFriend(friend);
        assertEquals(0, addressBook.numFriends());
    }
}
