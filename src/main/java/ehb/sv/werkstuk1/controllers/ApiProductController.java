package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.ProductDAO;
import ehb.sv.werkstuk1.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
public class ApiProductController {

    public ProductDAO productDAO;

    @Autowired
    public ApiProductController(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    @PostMapping("/api/product/create")
    public String createProduct(@RequestBody Product product) throws InterruptedException, ExecutionException {
        return productDAO.createProduct(product);
    }

    @GetMapping("/api/product/get")
    public Product getProduct(@RequestParam String name) throws InterruptedException, ExecutionException {
        return productDAO.getProduct(name);
    }

//    @GetMapping("/api/product/getname")
//    public Product getProductByName(@RequestParam String name) throws InterruptedException, ExecutionException {
//        return productDAO.getProductbyName(name);
//    }

    @GetMapping("/api/product/getAll")
    public ArrayList<Product> getAllProduct() throws InterruptedException, ExecutionException {
        return productDAO.getAllProducts();
    }

    @GetMapping("/api/product/getForAnimal")
    public ArrayList<Product> getAllProductByAnimal(@RequestParam String animal) throws InterruptedException, ExecutionException {
        return productDAO.getAllProductsforAnimal(animal);
    }

    @GetMapping("/api/product/getType")
    public ArrayList<Product> getAllProductsByType(@RequestParam String type) throws InterruptedException, ExecutionException {
        return productDAO.getAllProductsfortype(type);
    }

    @PutMapping("/api/product/update")
    public String updateProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return productDAO.updateProduct(product);
    }

    @DeleteMapping("/api/product/delete")
    public String deleteProduct(@RequestParam String name){
        return productDAO.deleteProduct(name);
    }

}

