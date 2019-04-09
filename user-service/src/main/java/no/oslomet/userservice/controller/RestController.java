package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.User;
import no.oslomet.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/id/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.deleteUserById(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User newUser){
        userService.saveUser(newUser);
        return newUser;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User newUser){
        newUser.setId(id);
        return userService.saveUser(newUser);
    }

    @GetMapping("/users/{id}")
    public User getUserByUserName(@PathVariable String id){
        return userService.getUserByUserName(id);
    }
}

