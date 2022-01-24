package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.UserDAO;
import ehb.sv.werkstuk1.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Controller
public class UserController {

    public UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/profile")
    public String home(ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            System.out.println(principal.getClaims());
        }
        return "profile";
    }

    @GetMapping("/cart")
    public String cart(ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {

        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            Cart cart = userDAO.getCart(principal.getEmail());
            modelMap.addAttribute("cart", cart);

            OrderPost orderPost = new OrderPost(principal.getEmail(), cart.calculateTotal());
            modelMap.addAttribute("orderPost", orderPost);
            return "cart";
        }
        return "products";
    }
//    not needed I think
    @PostMapping("/cart/create")
    public String createCart(@RequestBody Cart cart, @RequestParam String email) throws InterruptedException, ExecutionException {
        return userDAO.saveCart(cart, email);
    }
    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute CartPost cartPost, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());

        System.out.println("UserController.addToCart");
        System.out.println("cartPost = " + cartPost);
        Cart cart = userDAO.AddToCart(cartPost.getEmail(), cartPost.getName(), cartPost.getPrice(), cartPost.getAmount());
        modelMap.addAttribute("cart", cart);
        OrderPost orderPost = new OrderPost(principal.getEmail(), cart.calculateTotal());
        modelMap.addAttribute("orderPost", orderPost);
        }
        return "cart";
    }

    /**
     *  when clicking on order now button on cart page
     *  will redirect to check out page where personal details have to be filled in
     * @param orderPost (=email + total cost)
     * @param modelMap :)
     * @param principal :)
     * @return checkout page
     */
    @PostMapping("/checkout")
    public String checkout(@ModelAttribute OrderPost orderPost, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            modelMap.addAttribute("orderPost", orderPost);
        }
        modelMap.addAttribute("userDetails", new UserDetails());
        return "checkout";
    }

    /**
     * order word opgebouwd en naar de db verstuurd
     * de cart word erna leeggehaalt
     * @param userDetails all details of the user
     * @param modelMap :)
     * @param principal :)
     * @return confirmation page
     */
    @PostMapping("/confirm")
    public String confirm(@ModelAttribute UserDetails userDetails, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        if (principal != null && userDetails !=null) {
            modelMap.addAttribute("profile", principal.getClaims());
            Order order = new Order();
            order.setUserDetails(userDetails);
            order.setEmail(principal.getEmail());
            Cart cart = userDAO.getCart(principal.getEmail());
            order.setCart(cart);
            order.setPrice(cart.calculateTotal());
            String id = userDAO.saveOrder(order);
            if(!id.isEmpty()){
                userDAO.deleteCart(principal.getEmail());
            }
            modelMap.addAttribute("id", id);
        }
        return "confirmation";
    }

//    @PostMapping("/user/create")
//    public String createUser(@RequestBody User user) throws InterruptedException, ExecutionException {
//        return userDAO.createUser(user);
//    }
//
//    @GetMapping("/user/get")
//    public User getUser(@RequestParam String documentId) throws InterruptedException, ExecutionException {
//        return userDAO.getUser(documentId);
//    }
//
//    @PutMapping("/user/update")
//    public String updateUser(@RequestBody User user) throws ExecutionException, InterruptedException {
//        return userDAO.updateUser(user);
//    }
//
//    @DeleteMapping("/user/delete")
//    public String deleteUser(@RequestParam String documentId){
//        return userDAO.deleteUser(documentId);
//    }

}
