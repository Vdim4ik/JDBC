package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Connection connection = Util.getConnection();
        String sql = """
                CREATE TABLE IF NOT EXISTS pre_proj.user (
                  id BIGINT NOT NULL AUTO_INCREMENT,
                  name VARCHAR(45) NOT NULL,
                  lastName VARCHAR(45) NOT NULL,
                  age TINYINT NULL,
                  PRIMARY KEY (id));""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        String sql = "DROP TABLE IF EXISTS pre_proj.user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        String sql = "INSERT INTO pre_proj.user (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            connection.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        String sql = "DELETE FROM `pre_proj`.`user` WHERE id =" + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM pre_proj.user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        String sql = "DELETE FROM pre_proj.user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
