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

    /**
     * safely add an item to the array of items
     * if the item is already in the array then the amount will be increased
     * @param item item to add to the Array of items
     */
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

    public boolean deleteItem(String item){
        if(items.isEmpty()){
            return false;
        }
        CartItem toDel=new CartItem();
        for (CartItem arrayItem : items) {
            if (Objects.equals(arrayItem.getProduct(), item)) {
                toDel = arrayItem;
            }
        }
        return items.remove(toDel);
    }

    /**
     * calculate the total cost of the cart
     * @return (float) total cost
     */
    public float calculateTotal(){
        float total = 0;
        for (CartItem item : items) {
            total = total + (item.getAmount()* item.getPrice());
        }
        return total;
    }

    public boolean canCheckout(){
        return !items.isEmpty();
    }

//    --------------------------------------------- //

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(ArrayList<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
