package ehb.sv.werkstuk1.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import ehb.sv.werkstuk1.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CartDAO {

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
        return new Cart();
    }

    /**
     *  add item to cart in the DB
     * @param email the email of the logged-in user (who puts the item in their cart)
     * @param name the name of the product
     * @param price the price of the product
     * @param amount amount of the product
     * @return the updated cart
     * @throws ExecutionException :)
     * @throws InterruptedException :)
     */
    public Cart AddToCart(String email, String name, float  price, int amount) throws ExecutionException, InterruptedException {
        Cart cart = getCart(email);
        if(cart==null){
            cart = new Cart();
        }
        cart.addItem(new CartItem(name, price, amount));
        saveCart(cart, email);
        return cart;
    }

    public String deleteCart(String email){
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection("carts").document(email).delete();

        return "Successfully deleted cart of "+ email;
    }

    public String saveOrder(Order order) {
        Firestore db = FirestoreClient.getFirestore();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        ApiFuture<WriteResult> collectionsApiFuture = db.collection("orders").document(time).set(order);

        return time;
    }

    public ArrayList<Order> getOrders(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("orders").whereEqualTo("email", email).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        ArrayList<Order> orders = new ArrayList<>();

        for (DocumentSnapshot document : documents) {
            Order order;
            order = document.toObject(Order.class);
            orders.add(order);
        }
        return orders;
    }

//    --------------------------------------------------------------------

//    public String createUser(User user) throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionsApiFuture = db.collection("users").document(user.getEmail()).set(user);
//
//        return collectionsApiFuture.get().getUpdateTime().toString();
//    }

//    public User getUser(String documentId) throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreClient.getFirestore();
//        DocumentReference documentReference = db.collection("users").document(documentId);
//        ApiFuture<DocumentSnapshot> future = documentReference.get();
//        DocumentSnapshot document = future.get();
//        User user;
//        if(document.exists()){
//            user = document.toObject(User.class);
//            return user;
//        }
//        return null;
//    }
//    public String updateUser(User user) throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionsApiFuture = db.collection("users").document(user.getEmail()).set(user);
//        return collectionsApiFuture.get().getUpdateTime().toString();
//    }
//    public String deleteUser(String documentId){
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> writeResult = db.collection("users").document(documentId).delete();
//
//        return "Successfully deleted "+ documentId;
//    }
}
