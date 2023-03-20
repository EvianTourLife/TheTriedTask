package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS1 (ID BIGINT AUTO_INCREMENT  PRIMARY KEY, NAME VARCHAR(50)," +
                "LASTNAME VARCHAR(50)," + " AGE TINYINT (3))";
        connection = Util.getConnection();
        try (Statement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS1";
        connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO USERS1 (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        connection = Util.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM USERS1 WHERE ID=?";
        connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List list = new ArrayList();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS1";
        connection = Util.getConnection();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                list.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE USERS1";
        connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
