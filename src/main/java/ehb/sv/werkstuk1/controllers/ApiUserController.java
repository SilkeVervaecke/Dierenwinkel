package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.CartDAO;
import ehb.sv.werkstuk1.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class ApiUserController {

    public CartDAO cartDAO;

    @Autowired
    public ApiUserController(CartDAO cartDAO){
        this.cartDAO = cartDAO;
    }

//    @PostMapping("/api/user/create")
//    public String createUser(@RequestBody User user) throws InterruptedException, ExecutionException {
//        return userDAO.createUser(user);
//    }
    @GetMapping("/api/cart/get")
    public Cart getCart(@RequestParam String email) throws InterruptedException, ExecutionException {
        return cartDAO.getCart(email);
    }
    @PostMapping("/api/cart/create")
    public String createCart(@RequestBody Cart cart, @RequestParam String email) throws InterruptedException, ExecutionException {
        return cartDAO.saveCart(cart, email);
    }


//    @GetMapping("/api/user/get")
//    public User getUser(@RequestParam String documentId) throws InterruptedException, ExecutionException {
//        return userDAO.getUser(documentId);
//    }

//    @PutMapping("/api/user/update")
//    public String updateUser(@RequestBody User user) throws ExecutionException, InterruptedException {
//        return userDAO.updateUser(user);
//    }
//
//    @DeleteMapping("/api/user/delete")
//    public String deleteUser(@RequestParam String documentId){
//        return userDAO.deleteUser(documentId);
//    }

}
