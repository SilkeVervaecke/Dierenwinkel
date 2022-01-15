package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.UserDAO;
import ehb.sv.werkstuk1.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    public UserDAO userDAO;

    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userDAO.createUser(user);
    }

    @GetMapping("/get")
    public User getUser(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return userDAO.getUser(documentId);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return userDAO.updateUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam String documentId){
        return userDAO.deleteUser(documentId);
    }

}
