package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        try {
            userService.createUsersTable();
            userService.saveUser("Марат", "Кобольдов", (byte) 17);
            userService.saveUser("Саша", "Киреев", (byte) 26);
            userService.saveUser("Вадим", "Маск", (byte) 66);
            userService.saveUser("Сергей", "Дуров", (byte) 10);
            System.out.println(userService.getAllUsers());
            userService.cleanUsersTable();
            userService.dropUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
