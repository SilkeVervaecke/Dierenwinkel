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

}
