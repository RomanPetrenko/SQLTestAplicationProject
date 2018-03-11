package com.roman.petrenko;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.*;

public class DAOOracleImpl implements DAOModel {
    private static final DAOOracleImpl instance = new DAOOracleImpl();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private DAOOracleImpl(){}

    public static DAOOracleImpl getInstance (){
        return instance;
    }

    @Override
    public void connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Roman", "qwerty1997");
            if (!connection.isClosed()) {
                System.out.println("Connection successful");
            }
        } catch (ClassNotFoundException | SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getALLStudents() {
        connect();
        List<Student > list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT  * FROM STUDENTS");
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    list.add(parseStudent(resultSet));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return list;
    }

    private Student parseStudent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("STUDENT_ID");
        String name = resultSet.getString("STUDENT_NAME");
        String group = resultSet.getString("STUDENT_GROUP");
        return new Student(id,name,group);
    }

    @Override
    public void updateStudentById(int id) {
        connect();
        String updateTableSQL = "UPDATE STUDENTS SET STUDENT_NAME = ? "
                + " WHERE STUDENT_ID = ?";
            try {
                preparedStatement = connection.prepareStatement(updateTableSQL);

                preparedStatement.setString(1, "Ivan");
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public void removeStudentById(int id) {
        connect();
        String removeTableSQL = "DELETE FROM STUDENTS WHERE STUDENT_ID = ?";
        try {
            preparedStatement = connection.prepareStatement(removeTableSQL);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public void insertStudent(Student student) {
        connect();
        String insertTableSQL = "INSERT INTO STUDENTS"
                + "(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP) VALUES"
                + "(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getGroup());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }
}
