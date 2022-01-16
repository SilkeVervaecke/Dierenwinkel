package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.ProductDAO;
import ehb.sv.werkstuk1.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProductController {

//    public ProductDAO productDAO;

//    public ProductController(ProductDAO productDAO){
//        this.productDAO = productDAO;
//    }

    @Autowired
    private ProductDAO productDAO;

    @PostMapping("/product/create")
    public String createProduct(@RequestBody Product product) throws InterruptedException, ExecutionException {
        return productDAO.createProduct(product);
    }

    @GetMapping("/product/get")
    public Product getProduct(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return productDAO.getProduct(documentId);
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
    public String deleteProduct(@RequestParam String documentId){
        return productDAO.deleteProduct(documentId);
    }

}
