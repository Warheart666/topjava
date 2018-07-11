package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersUtil {

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Set<Role> admins = new HashSet<>();
        admins.add(Role.ROLE_ADMIN);

        users.add(new User(0, "FirstUsername", "firstEmail@mail.jv", "adminpass", 2000, true, admins));
        users.add(new User(1, "SecondUsername", "secEmail@mail.jv", "admin2pass", 3000, true, admins));
        users.add(new User(2, "SUser", "secEmail@mail3.jv", "admin2pass", 3000, false, admins));
        users.add(new User(3, "name4", "secEmail@mail2.jv", "admin2pass", 3000, true, admins));
        users.add(new User(4, "1name", "secEmail@mail1.jv", "pass", 3000, false, admins));

        return users;
    }


}
