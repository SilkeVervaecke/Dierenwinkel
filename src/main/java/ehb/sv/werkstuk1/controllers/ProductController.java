package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.ProductDAO;
import ehb.sv.werkstuk1.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "products";
    }

    @GetMapping("/products/getForAnimal")
    public String getForAnimal(@RequestParam String animal, ModelMap modelMap) throws ExecutionException, InterruptedException {
        modelMap.addAttribute("products", productDAO.getAllProductsforAnimal(animal));
        return "products";
    }
    @GetMapping("/products/getType")
    public String getType(ModelMap modelMap, @RequestParam String type) throws ExecutionException, InterruptedException {
        modelMap.addAttribute("products", productDAO.getAllProductsfortype(type));
        return "products";
    }

    @GetMapping("/products/get")
    public String getProduct(ModelMap modelMap, @RequestParam String name, @AuthenticationPrincipal OidcUser principal) throws InterruptedException, ExecutionException {
        modelMap.addAttribute("product", productDAO.getProduct(name));
        if (principal != null) {
            modelMap.addAttribute("profile", principal.getClaims());
        }
        return "productDetail";
    }

// ------------------------------------------------------------------------------------------------------------------------------------- //

    @PostMapping("/product/create")
    public String createProduct(@RequestBody Product product) throws InterruptedException, ExecutionException {
        return productDAO.createProduct(product);
    }

    @GetMapping("/product/getAll")
    public ArrayList<Product> getAllProduct() throws InterruptedException, ExecutionException {
        return productDAO.getAllProducts();
    }

    @PutMapping("/product/update")
    public String updateProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return productDAO.updateProduct(product);
    }

    @DeleteMapping("/product/delete")
    public String deleteProduct(@RequestParam String name){
        return productDAO.deleteProduct(name);
    }

}
