package ehb.sv.werkstuk1.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import ehb.sv.werkstuk1.models.Cart;
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

    public String cart(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ArrayList<Object> array = new ArrayList<>();

        Map<String, Object> docData = new HashMap<>();
        docData.put("amount", 5);
        docData.put("product", "product9");

        array.add(docData);
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("carts").document(user.getEmail()).set(user);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public Cart getCart(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection("carts").document(email);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Cart cart;
        System.out.println("get dem carts");
        if(document.exists()){
            System.out.println(document);
            Object obj = document.toObject(Object.class);
            System.out.println(obj);

            System.out.println(document);
            System.out.println(document);
            cart = document.toObject(Cart.class);
            System.out.println(cart);
            return cart;
        }
        return null;
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
