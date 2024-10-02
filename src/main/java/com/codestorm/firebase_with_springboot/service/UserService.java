package com.codestorm.firebase_with_springboot.service;

import com.codestorm.firebase_with_springboot.dao.User;
import com.codestorm.firebase_with_springboot.dto.UserDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.codestorm.firebase_with_springboot.utils.UserUtils.getUserList;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final Firestore firestore;

    //CREATE
    public String createUser(User user) {
        try {
            ApiFuture<DocumentReference> users = firestore.collection("users").add(user);
            // firestore.collection("users").document().set(user);
            return "Document saved: userId is " + users.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // READ
    public User getUser(String id) {
        try {
            ApiFuture<DocumentSnapshot> users = firestore.collection("users").document(id).get();
            return users.get().toObject(User.class);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    public String updateUser(User user) {
        // 1. Check if ID exists
        // 2. Check if user is valid
        try {
            ApiFuture<WriteResult> users = firestore.collection("users")
                    .document(user.getId())
                    .update("username", user.getUsername());

            return "User updated at: " + users.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //DELETE
    public String deleteUser(String id) {
        try {
            ApiFuture<WriteResult> users = firestore.collection("users").document(id).delete();
            return "User deleted at : " + users.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDto findByEmailId(String email) {
        if (email == null || email.isBlank()) {
            log.error("Email is invalid");
            throw new RuntimeException("Email is invalid!!");
        }

        try {
            // whereNotEqualTo
            Query query = firestore.collection("users").whereEqualTo("emailId", email);
            List<User> userList = getUserList(query);
            return UserDto.builder().users(userList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserDto findSalary(double salary) {
        if (salary <= 0) {
            log.error("Salary is invalid");
            throw new RuntimeException("Salary is invalid!!");
        }
        try {
            //whereLessThan, whereGreaterThan, whereLessThanOrEqualTo, whereGreaterThanOrEqualTo
            Query query = firestore.collection("users").whereGreaterThanOrEqualTo("salary", salary);
            List<User> userList = getUserList(query);
            return UserDto.builder().users(userList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserDto findUserByRole(String role) {
        try {
            Query query = firestore.collection("users").whereArrayContains("roles", role);
            List<User> userList = getUserList(query);
            return UserDto.builder().users(userList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserDto findUserByMultipleRole(List<String> roles) {
        try {
            Query query = firestore.collection("users").whereArrayContainsAny("roles", roles);
            List<User> userList = getUserList(query);
            return UserDto.builder().users(userList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserDto findUserByIn(List<String> names) {
        try {
            //whereNotIn
            Query query = firestore.collection("users").whereIn("username", names);
            List<User> userList = getUserList(query);
            return UserDto.builder().users(userList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
