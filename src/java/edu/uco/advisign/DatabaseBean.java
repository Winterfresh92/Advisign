
package edu.uco.advisign;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;

//Access the database through this bean

@Named
@ApplicationScoped
public class DatabaseBean implements Serializable {
    
    private ArrayList<StudentBean> students;
    private ArrayList<String> majors;
    private ArrayList<Course> courses;
    
    @Resource(name = "jdbc/Advisign")
    private DataSource ds;
    
    public DatabaseBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            students = generateStudentList();
            majors = generateMajorsList();
            courses = generateCourseList();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public ArrayList<StudentBean> generateStudentList() throws SQLException {
        if (ds == null) {
            throw new SQLException("ds is null; Can't get data source");
        }

        Connection conn = ds.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }
        
        students = new ArrayList<>();
        
        try {
            PreparedStatement ps = conn.prepareStatement("select student_id, first_name, last_name, password,"
                    + " email, major from student join user_table on student_id = user_id");

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                StudentBean s = new StudentBean();
                s.setStudentId(result.getInt("student_id"));
                s.setFirstName(result.getString("first_name"));
                s.setLastName(result.getString("last_name"));
                s.setPassword(result.getString("password"));
                s.setEmail(result.getString("email"));
                s.setMajor(result.getString("major"));
                students.add(s);
            }

        } finally {
            conn.close();
        }
        
        return students;
    }
    
    public ArrayList<String> generateMajorsList() throws SQLException {
        if (ds == null) {
            throw new SQLException("ds is null; Can't get data source");
        }

        Connection conn = ds.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }
        
        majors = new ArrayList<>();
        
        try {
            PreparedStatement ps = conn.prepareStatement("select major_code, major_title from major_info");

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String s = result.getString("major_code") + " - " + result.getString("major_title");
                majors.add(s);
            }

        } finally {
            conn.close();
        }
        
        return majors;
    }
    
    public ArrayList<Course> generateCourseList() throws SQLException {
        if (ds == null) {
            throw new SQLException("ds is null; Can't get data source");
        }

        Connection conn = ds.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }
        
        courses = new ArrayList<>();
        
        try {
            PreparedStatement ps = conn.prepareStatement("select course_prefix, course_id, course_name from major_info");

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Course temp = new Course();
                temp.setPrefix(result.getString("course_prefix"));
                temp.setId(result.getInt("course_id"));
                temp.setName(result.getString("course_name"));
                courses.add(temp);
            }

        } finally {
            conn.close();
        }
        
        return courses;
    }

    public ArrayList<StudentBean> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentBean> students) {
        this.students = students;
    }

    public ArrayList<String> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<String> majors) {
        this.majors = majors;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    
}
