package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
@Getter
@Setter
public class Cart {
    private ArrayList<CartItem> items;
//    private Timestamp timeCreated;

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
//                ", timeCreated=" + timeCreated +
                '}';
    }
}