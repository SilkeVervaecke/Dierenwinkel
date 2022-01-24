package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderPost {
//    private Cart cart;
    private String email;
    private float price;


    //    --------------------------------------------- //

    public OrderPost() {
    }

    public OrderPost(String email, float price) {
        this.email = email;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "email='" + email + '\'' +
                ", price=" + price +
                '}';
    }
}
