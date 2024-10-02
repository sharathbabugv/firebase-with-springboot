package com.codestorm.firebase_with_springboot.utils;

import com.codestorm.firebase_with_springboot.dao.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserUtils {

    public static List<User> getUserList(Query query) throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshotApiFuture.get().getDocuments();
        List<User> userList = new ArrayList<>();
        for (QueryDocumentSnapshot queryDocumentSnapshot : documents) {
            User users = queryDocumentSnapshot.toObject(User.class);
            userList.add(users);
        }
        return userList;
    }
}
