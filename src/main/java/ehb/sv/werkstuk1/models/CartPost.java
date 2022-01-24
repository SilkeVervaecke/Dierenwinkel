package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartPost {
//    entity so postrequest can happen to put somthing into your cart
    private String name;
    private float price;
    private String email;
    private int amount;



    //    --------------------------------------------- //

    public CartPost() {
    }

    public CartPost(String name, float price, String email, int amount) {
        this.name = name;
        this.price = price;
        this.email = email;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartPost{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", email='" + email + '\'' +
                ", amount=" + amount +
                '}';
    }
}
