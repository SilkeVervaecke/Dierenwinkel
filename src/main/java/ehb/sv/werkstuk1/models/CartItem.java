package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CartItem {
    private String product;
    private float price;
    private int amount;

    public CartItem(String product, float price, int amount) {
        this.product = product;
        this.price = price;
        this.amount = amount;
    }

    public CartItem() {
    }
}
