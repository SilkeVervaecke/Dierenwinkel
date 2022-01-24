package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

    private String name;
    private String street;
    private String city;
    private String country;
    private String zipcode;
    private String card;

    public  UserDetails() {
    }

    public UserDetails(String name, String street, String city, String country, String zipcode, String card) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.card = card;
    }
}
