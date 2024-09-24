package com.codestorm.firebase_with_springboot.controller;

import com.codestorm.firebase_with_springboot.dao.User;
import com.codestorm.firebase_with_springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
