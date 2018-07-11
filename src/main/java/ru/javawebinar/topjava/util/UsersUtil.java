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

        users.add(new User(null, "FirstUsername", "firstEmail@mail.jv", "adminpass", 2000, true, admins));
        users.add(new User(null, "SecondUsername", "secEmail@mail.jv", "admin2pass", 3000, true, admins));
        users.add(new User(null, "SUser", "secEmail@mail3.jv", "admin2pass", 3000, false, admins));
        users.add(new User(null, "name4", "secEmail@mail2.jv", "admin2pass", 3000, true, admins));
        users.add(new User(null, "1name", "secEmail@mail1.jv", "pass", 3000, false, admins));

        return users;
    }


}
