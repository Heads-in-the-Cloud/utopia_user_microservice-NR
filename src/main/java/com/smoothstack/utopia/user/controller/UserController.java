package com.smoothstack.utopia.user.controller;

import com.smoothstack.utopia.user.entity.User;
import com.smoothstack.utopia.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('Administrator')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<User> findUserById(@PathVariable int id) {
        Optional<User> u = userService.findUserById(id);
        return u.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Optional<User> u = userService.addUser(user);
        return u.map(value -> ResponseEntity.status(201).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PutMapping(path = "/edit/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<User> updateUserById(@PathVariable int id, @RequestBody User user) {
        Optional<User> u = userService.updateUserById(id, user);
        return u.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
