package ehb.sv.werkstuk1.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.NotNull;
import ehb.sv.werkstuk1.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductDAO {

    public String createProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("products").document(product.getName()).set(product);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Product getProduct(String name) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection("products").document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Product product;
        if(document.exists()){
            product = document.toObject(Product.class);
            return product;
        }
        return null;
    }

    // [DEPRECATED]
    public Product getProductbyName(String name) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("products").whereEqualTo("name", name).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        Product product;
        if(!documents.isEmpty()){
            product = documents.get(0).toObject(Product.class);
            return product;
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("products").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        ArrayList<Product> products = new ArrayList<>();

        for (DocumentSnapshot document : documents) {
            Product product;
            product = document.toObject(Product.class);
            products.add(product);
        }
        return products;
    }
    public ArrayList<Product> getAllProductsforAnimal(String animal) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("products").whereArrayContains("forAnimals", animal.toLowerCase()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        ArrayList<Product> products = new ArrayList<>();

        for (DocumentSnapshot document : documents) {
            Product product;
            product = document.toObject(Product.class);
            products.add(product);
        }
        return products;
    }
    public ArrayList<Product> getAllProductsfortype(String type) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("products").whereEqualTo("type", type).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        ArrayList<Product> products = new ArrayList<>();

        for (DocumentSnapshot document : documents) {
            Product product;
            product = document.toObject(Product.class);
            products.add(product);
        }
        return products;
    }
    public ArrayList<Product> getAllProductsBetweenPrice(int min, int max) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future;
        if(max==0){
             future = db.collection("products").whereGreaterThanOrEqualTo("price", min).get();

        } else{
             future = db.collection("products").whereLessThanOrEqualTo("price", max).whereGreaterThanOrEqualTo("price", min).get();
        }
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        ArrayList<Product> products = new ArrayList<>();

        for (DocumentSnapshot document : documents) {
            Product product;
            product = document.toObject(Product.class);
            products.add(product);
        }
        return products;
    }

    public String orderProduct(@NotNull Product product, int amount) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        product.setAmount(product.getAmount()-amount);
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("products").document(product.getName()).set(product);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }


    public String updateProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("products").document(product.getName()).set(product);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String deleteProduct(String name){
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection("products").document(name).delete();

        return "Successfully deleted product: "+ name;
    }


}
