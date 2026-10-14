package Group28;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuddyInfoTest {

    @Test
    void testConstructor() {
        BuddyInfo buddy = new BuddyInfo("Pork", "123");

        assertEquals("Pork", buddy.getName());
        assertEquals("123", buddy.getNumber());
    }

    @Test
    void testChangeNameAndNumber() {
        BuddyInfo buddy = new BuddyInfo("Pork", "123");
        assertEquals("Pork", buddy.getName());
        assertEquals("123", buddy.getNumber());

        buddy.setName("John");
        buddy.setNumber("456");
        assertEquals("John", buddy.getName());
        assertEquals("456", buddy.getNumber());

    }


}
