package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
public class Product {
    private String documentId;
    private String name;
    private String type;
    private Arrays foranimalslist;

}
