package Group28;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookApplication {

    private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BuddyInfoRepository buddyRepo,
                                  AddressBookRepository bookRepo) {
        return (args) -> {
            BuddyInfo b1 = new BuddyInfo("John", "123456789");
            BuddyInfo b2 = new BuddyInfo("Pete", "987654321");
            BuddyInfo b3 = new BuddyInfo("Alice", "555-0001");
            BuddyInfo b4 = new BuddyInfo("Bob",   "555-0002");
            buddyRepo.save(b1);
            buddyRepo.save(b2);
            buddyRepo.save(b3);
            buddyRepo.save(b4);

            AddressBook book = new AddressBook();
            book.addFriend(b1);
            book.addFriend(b2);
            bookRepo.save(book);

            AddressBook book2 = new AddressBook();
            book2.addFriend(b3);
            book2.addFriend(b4);
            bookRepo.save(book2);

            log.info("");
            log.info("Buddies found with findAll():");
            log.info("-------------------------------");
            buddyRepo.findAll().forEach(b -> log.info("  " + b));


            BuddyInfo buddy = buddyRepo.findById(1L);
            log.info("");
            log.info("Buddy found with findById(1L):");
            log.info("--------------------------------");
            log.info("  " + buddy);

            log.info("");
            log.info("Address books found with findAll():");
            log.info("-------------------------------");
            bookRepo.findAll().forEach(a -> log.info("  " + a));
            log.info("");

        };
    }
}
