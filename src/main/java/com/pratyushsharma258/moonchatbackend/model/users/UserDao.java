package com.pratyushsharma258.moonchatbackend.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserDao {
    private Long id;
    private String username;

    private static UserDao userDaoFromUser(User user) {
        return new UserDao(user.getId(), user.getUsername());
    }

    public static List<UserDao> userDaosFromUsers(List<User> users) {
        return users.stream().map(UserDao::userDaoFromUser).collect(Collectors.toList());
    }
}