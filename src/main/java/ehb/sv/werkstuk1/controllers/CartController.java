package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.CartDAO;
import ehb.sv.werkstuk1.dao.ProductDAO;
import ehb.sv.werkstuk1.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Controller
public class CartController {


    @Autowired
    public ProductDAO productDAO;

    public CartDAO cartDAO;
    @Autowired
    public CartController(CartDAO cartDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }


    @GetMapping("/profile")
    public String profile(ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            System.out.println("CartController.profile");
            ArrayList<Order> orders = cartDAO.getOrders(principal.getEmail());
            modelMap.addAttribute("orders", orders);
        }
        return "profile";
    }

    @GetMapping("/cart")
    public String cart(ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {

        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            Cart cart = cartDAO.getCart(principal.getEmail());
            modelMap.addAttribute("cart", cart);

            OrderPost orderPost = new OrderPost(principal.getEmail(), cart.calculateTotal());
            modelMap.addAttribute("orderPost", orderPost);
            return "cart";
        }
        modelMap.addAttribute("message", "You have to be logged in to see your cart");
        return "index";
    }

    /**
     * cart word opgehaald van de db, het item word gedelete
     * daarna word de geupdate cart terug in de db gezet
     * @param item the name of the item to delete
     * @param modelMap :)
     * @param principal :)
     * @return cart page if logged in, index if not logged in
     */
    @PostMapping("/cart/delete/{item}")
    public String cartDeleteItem(@PathVariable(value = "item")String item, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {

        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            Cart cart = cartDAO.getCart(principal.getEmail());
            cart.deleteItem(item);
            cartDAO.saveCart(cart, principal.getEmail());
            modelMap.addAttribute("cart", cart);
            String message = item+" has been deleted";
            modelMap.addAttribute("message", message);

            OrderPost orderPost = new OrderPost(principal.getEmail(), cart.calculateTotal());
            modelMap.addAttribute("orderPost", orderPost);

            return "cart";
        }
        modelMap.addAttribute("message", "You have to be logged in to see your cart");
        return "index";
    }


    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute CartPost cartPost, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            Cart cart = cartDAO.AddToCart(cartPost.getEmail(), cartPost.getName(), cartPost.getPrice(), cartPost.getAmount());
            modelMap.addAttribute("cart", cart);
            OrderPost orderPost = new OrderPost(principal.getEmail(), cart.calculateTotal());
            modelMap.addAttribute("orderPost", orderPost);
        }
        return "cart";
    }

    /**
     * when clicking on order now button on cart page
     * will redirect to check out page where personal details have to be filled in
     *
     * @param orderPost (=email + total cost)
     * @param modelMap  :)
     * @param principal :)
     * @return checkout page
     */
    @PostMapping("/checkout")
    public String checkout(@ModelAttribute OrderPost orderPost, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) {
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
     * de georderde items worden ook verwijderd uit de stock (amount bij product)
     * @param userDetails all details of the user
     * @param modelMap    :)
     * @param principal   :)
     * @return confirmation page
     */
    @PostMapping("/confirm")
    public String confirm(@ModelAttribute UserDetails userDetails, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        if (principal != null && userDetails != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            Order order = new Order();
            order.setUserDetails(userDetails);
            order.setEmail(principal.getEmail());
            Cart cart = cartDAO.getCart(principal.getEmail());
            order.setCart(cart);
            order.setPrice(cart.calculateTotal());
            deleteFromStock(cart);
            String id = cartDAO.saveOrder(order);
            if (!id.isEmpty()) {
                cartDAO.deleteCart(principal.getEmail());
            } else {
                modelMap.addAttribute("message", "Something went wrong, try again");
                return "index";
            }
            modelMap.addAttribute("id", id);
        }
        return "confirmation";
    }

    private void deleteFromStock(Cart cart) throws ExecutionException, InterruptedException {
        for(CartItem item : cart.getItems()){
            Product prod = productDAO.getProduct(item.getProduct());
            productDAO.orderProduct(prod, item.getAmount());
        }
    }

    //    not needed I think
//    @PostMapping("/cart/create")
//    public String createCart(@RequestBody Cart cart, @RequestParam String email) throws InterruptedException, ExecutionException {
//        return cartDAO.saveCart(cart, email);
//    }
// -----------------------------------------------------------------------------------------------------------//
//    USER IS NOT USED ANYMORE
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
