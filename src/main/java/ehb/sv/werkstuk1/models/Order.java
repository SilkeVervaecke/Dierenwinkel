package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order {
    private String email;
    private Cart cart;
    private float price;
    private UserDetails userDetails;


    public Order() {
    }

    public Order(String email, Cart cart, float price, UserDetails userDetails) {
        this.email = email;
        this.cart = cart;
        this.price = price;
        this.userDetails = userDetails;
    }
}
