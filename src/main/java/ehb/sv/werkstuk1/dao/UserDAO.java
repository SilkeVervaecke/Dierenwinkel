package ehb.sv.werkstuk1.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import ehb.sv.werkstuk1.models.Cart;
import ehb.sv.werkstuk1.models.CartItem;
import ehb.sv.werkstuk1.models.Product;
import ehb.sv.werkstuk1.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserDAO {

    public String createUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("users").document(user.getEmail()).set(user);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String saveCart(Cart cart, String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("carts").document(email).set(cart);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public Cart getCart(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection("carts").document(email);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Cart cart;
        if(document.exists()){
            cart = document.toObject(Cart.class);
            System.out.println(cart);
            return cart;
        }
        return null;
    }
    public String AddToCart(String email, Product product, int amount) throws ExecutionException, InterruptedException {
        Cart cart = getCart(email);
        if(cart==null){
            cart = new Cart();
        }
        cart.addItem(new CartItem(product.getName(), product.getPrice(), amount));
        return saveCart(cart, email);
    }

    public User getUser(String documentId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection("users").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User user;
        if(document.exists()){
            user = document.toObject(User.class);
            return user;
        }
        return null;
    }
    public String updateUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("users").document(user.getEmail()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String deleteUser(String documentId){
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection("users").document(documentId).delete();

        return "Successfully deleted "+ documentId;
    }
}
