package com.skillnext;

import java.sql.*;
import java.util.*;

public class StudentDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/skillnext_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            // Ensure MySQL driver is registered (helpful when the ServiceLoader is not triggered)
            Class.forName("com.mysql.cj.jdbc.Driver");
            ensureTable();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found on classpath", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database/table", e);
        }
    }

    private static void ensureTable() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS student (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "sem INT NOT NULL," +
                "dept VARCHAR(50) NOT NULL" +
                ")";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }
    
    public void AddStudent(Student st) throws Exception
    {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = conn.prepareStatement(
                     "INSERT INTO student (name, sem, dept) VALUES (?, ?, ?)")) {
            pst.setString(1, st.getName());
            pst.setInt(2, st.getSem());
            pst.setString(3, st.getDept());
            pst.executeUpdate();
        }
    }
    
    public void DeleteStudent(Student st) throws Exception
    {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = conn.prepareStatement(
                     "DELETE FROM student WHERE id=?")) {
            pst.setInt(1, st.getId());
            pst.executeUpdate();
        }
    }
    
    public void UpdateStudent(Student st) throws Exception
    {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = conn.prepareStatement(
                     "UPDATE student SET name=?, sem=?, dept=? WHERE id=?")) {
            pst.setString(1, st.getName());
            pst.setInt(2, st.getSem());
            pst.setString(3, st.getDept());
            pst.setInt(4, st.getId());
            pst.executeUpdate();
        }
    }
    
    public List<Student> ShowAllStudents() throws Exception
    {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM student");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setSem(rs.getInt("sem"));
                s.setDept(rs.getString("dept"));
                students.add(s);
            }
        }
        return students;
    }

}
