package ehb.sv.werkstuk1.controllers;

import ehb.sv.werkstuk1.dao.UserDAO;
import ehb.sv.werkstuk1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    public UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @PostMapping("/user/create")
    public String createUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userDAO.createUser(user);
    }

    @GetMapping("/user/get")
    public User getUser(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return userDAO.getUser(documentId);
    }

    @PutMapping("/user/update")
    public String updateUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return userDAO.updateUser(user);
    }

    @DeleteMapping("/user/delete")
    public String deleteUser(@RequestParam String documentId){
        return userDAO.deleteUser(documentId);
    }

}
