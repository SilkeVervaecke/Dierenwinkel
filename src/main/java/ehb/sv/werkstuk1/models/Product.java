package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Product {
    private String name;
    private String type;
    private ArrayList<String> forAnimals;
    private float amount; //just to be sure
    private float price;

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", forAnimals=" + forAnimals +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
