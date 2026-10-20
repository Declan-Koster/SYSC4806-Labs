package Group28;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressBookController {

    private final AddressBookRepository repository;

    public AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
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


}
