package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.ProductDAO;
import ehb.sv.werkstuk1.models.CartPost;
import ehb.sv.werkstuk1.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Controller
public class ProductController {

    public ProductDAO productDAO;

    @Autowired
    public ProductController(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    @GetMapping("/products")
    public String home(ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {
        modelMap.addAttribute("products", productDAO.getAllProducts());
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
        }
        return "products";
    }

    @GetMapping("/products/getForAnimal")
    public String getForAnimal(@RequestParam String animal, ModelMap modelMap, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {
        modelMap.addAttribute("products", productDAO.getAllProductsforAnimal(animal));
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
        }
        return "products";
    }
    @GetMapping("/products/getType")
    public String getType(ModelMap modelMap, @RequestParam String type, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {
        modelMap.addAttribute("products", productDAO.getAllProductsfortype(type));
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
        }
        return "products";
    }
    @GetMapping("/products/getPrice")
    public String getPrice(ModelMap modelMap, @RequestParam int min,@RequestParam int max, @AuthenticationPrincipal OidcUser principal) throws ExecutionException, InterruptedException {

        modelMap.addAttribute("products", productDAO.getAllProductsBetweenPrice(min, max));
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
        }
        return "products";
    }

    @GetMapping("/products/get")
    public String getProduct(ModelMap modelMap, @RequestParam String name, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        Product product = productDAO.getProduct(name);
        modelMap.addAttribute("product", product);
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
            CartPost cartPost = new CartPost();
            cartPost.setEmail(principal.getEmail());
            cartPost.setName(product.getName());
            cartPost.setPrice(product.getPrice());
            modelMap.addAttribute("cartPost", cartPost);
        }
        return "productDetail";
    }

}
