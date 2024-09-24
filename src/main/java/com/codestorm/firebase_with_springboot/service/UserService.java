package com.codestorm.firebase_with_springboot.service;

import com.codestorm.firebase_with_springboot.dao.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private Firestore firestore;

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
}
