package com.codestorm.firebase_with_springboot.dto;

import com.codestorm.firebase_with_springboot.dao.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private List<User> users;
}
