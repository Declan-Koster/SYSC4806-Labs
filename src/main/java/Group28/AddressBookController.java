package Group28;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressBookController {

    private final AddressBookRepository repository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookController(AddressBookRepository repository, BuddyInfoRepository buddyInfoRepository) {
        this.repository = repository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    @GetMapping("/addressBooks/{id}/view")
    public String viewAddressBook(@PathVariable long id, Model model) {
        AddressBook addressBook = repository.findById(id);
        model.addAttribute("addressBook",addressBook);
        return "addressbook";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("addressBooks", repository.findAll());
        return "index";
    }

    @PostMapping("/addressBooks/create")
    public String createAddressBook() {
        repository.save(new AddressBook());
        return "redirect:/";
    }

    @PostMapping("/addressBooks/{id}/friends")
    public String addBuddy(@PathVariable long id, @RequestParam String name, @RequestParam String number, @RequestParam(required = false) String address) {
        AddressBook addressBook = repository.findById(id);
        BuddyInfo buddy = new BuddyInfo(name, number);

        buddy.setAddress(address);
        buddyInfoRepository.save(buddy);
        addressBook.addFriend(buddy);
        repository.save(addressBook);

        return "redirect:/addressBooks/" + id + "/view";
    }

}
