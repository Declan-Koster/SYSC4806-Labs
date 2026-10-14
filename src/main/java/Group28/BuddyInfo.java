package Group28;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String number;
    private String address;

    public BuddyInfo() {
    }

    public BuddyInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {return address;}

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAddress(String address) {this.address = address;}

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return name + ":" + number;
    }
}
