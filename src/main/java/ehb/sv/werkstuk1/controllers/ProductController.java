package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.ProductDAO;
import ehb.sv.werkstuk1.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProductController {

    public ProductDAO productDAO;

    public ProductController(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    @PostMapping("/create")
    public String createProduct(@RequestBody Product product) throws InterruptedException, ExecutionException {
        return productDAO.createProduct(product);
    }

    @GetMapping("/get")
    public Product getProduct(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return productDAO.getProduct(documentId);
    }

    @GetMapping("/getAll")
    public ArrayList<Product> getAllProduct() throws InterruptedException, ExecutionException {
        return productDAO.getAllProducts();
    }

    @PutMapping("/update")
    public String updateProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return productDAO.updateProduct(product);
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam String documentId){
        return productDAO.deleteProduct(documentId);
    }

}
