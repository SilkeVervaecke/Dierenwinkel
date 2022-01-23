package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Cart {
    private ArrayList<CartItem> items;

    public void addItem(CartItem item) {
        if(items==null){
            items = new ArrayList<>();
        }
        boolean inArray=false;
        for (CartItem arrayItem : items) {
            if (Objects.equals(arrayItem.getProduct(), item.getProduct())) {
                arrayItem.setAmount(arrayItem.getAmount()+item.getAmount());
                inArray = true;
            }
        }
        if(!inArray){
            items.add(item);
        }
    }

    public Cart() {
        items = new ArrayList<CartItem>();
    }

    public Cart(ArrayList<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
//                ", timeCreated=" + timeCreated +
                '}';
    }
}
