package ehb.sv.werkstuk1.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private String nickname;
    private String email;
    private Cart cart;
}
