package ehb.sv.werkstuk1.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import ehb.sv.werkstuk1.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductDAO {
    public String createProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("products").document(product.getDocumentId()).set(product);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public Product getProduct(String documentId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection("products").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Product product;
        if(document.exists()){
            product = document.toObject(Product.class);
            return product;
        }
        return null;
    }
    public ArrayList<Product> getAllProducts() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("products").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        ArrayList<Product> products = new ArrayList<Product>();

        for (DocumentSnapshot document : documents) {
            Product product;
            product = document.toObject(Product.class);
            products.add(product);
            System.out.println(document.getId() + " => " + document.toObject(Product.class));
        }
        return products;

    }

    public String updateProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("products").document(product.getDocumentId()).set(product);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String deleteProduct(String documentId){
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection("products").document(documentId).delete();

        return "Successfully deleted product: "+ documentId;
    }


}
