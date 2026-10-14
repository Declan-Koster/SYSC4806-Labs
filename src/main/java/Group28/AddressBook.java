package Group28;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_book_id")
    public List<BuddyInfo> friends;

    public AddressBook() {
        friends = new ArrayList<>();
    }

    public void addFriend(BuddyInfo friend)  {
        friends.add(friend);
    }

    public void removeFriend(BuddyInfo friend) {
        friends.remove(friend);
    }

    public int numFriends() {
        return friends.size();
    }

    public List<BuddyInfo> getFriends() {
        return friends;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return friends.toString();
    }

}
