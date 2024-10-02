package com.codestorm.firebase_with_springboot.dao;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Data;

import java.util.List;

@Data
public class User {

    @DocumentId
    private String id;
    private String username;
    private String emailId;
    private Integer age;
    private List<String> roles;
    private double salary;
    private String currency;

    @ServerTimestamp
    private Timestamp createdAt;
}
