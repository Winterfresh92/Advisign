package edu.uco.advisign;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

@Named
@SessionScoped
public class StudentBean implements Serializable {
    
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private int studentId;
    private String major;
    private String verificationCode;
    private Course tempCourse;
    private ArrayList<Course> completedCourses;

    @Resource(name = "jdbc/Adv")
    private DataSource ds;
    
    public StudentBean() {
    }
    
    @PostConstruct
    public void init() {
        tempCourse = new Course();
        try {
            completedCourses = generateCompletedCourses();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String editCourse(Course course) {
        course.setEditable(true);
        return null;
    }
    
    public String saveCourse(Course course) throws SQLException {
        if (ds == null) {
            throw new SQLException("ds is null; Can't get data source");
        }

        Connection conn = ds.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }
        
        try {
            conn.setAutoCommit(false);
            boolean committed = false;
            try {
                PreparedStatement updateStatement = conn.prepareStatement("update completed_course set course_prefix = ?, course_id = ?, course_name = ?"
                        + ", semester = ?, credit_hours = ? where com_course_id = ?");
                
                updateStatement.setString(1, course.getPrefix());
                updateStatement.setInt(2, course.getId());
                updateStatement.setString(3, course.getName());
                updateStatement.setString(4, course.getSemester());
                updateStatement.setInt(5, course.getCredits());
                updateStatement.setInt(6, course.getComId());
                
                updateStatement.executeUpdate();
                
                conn.commit();
                committed = true;
            } finally {
                if(!committed) {
                    conn.rollback();
                }
            }
        } finally {
            conn.close();
        }
                
        course.setEditable(false);
        completedCourses = generateCompletedCourses();
        
        return null;
    }
    
    public String add() throws SQLException {
        if (ds == null) {
            throw new SQLException("ds is null; Can't get data source");
        }

        Connection conn = ds.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }
        
        try {
            conn.setAutoCommit(false);
            boolean committed = false;
            try {
                PreparedStatement insertStatement = conn.prepareStatement("insert into completed_course(course_prefix, course_id, course_name,"
                        + "student_id, semester, credit_hours)"
                        + " values(?, ?, ?, ?, ?, ?)");
                
                insertStatement.setString(1, tempCourse.getPrefix());
                insertStatement.setInt(2, tempCourse.getId());
                insertStatement.setString(3, tempCourse.getName());
                insertStatement.setInt(4, studentId);
                insertStatement.setString(5, tempCourse.getSemester());
                insertStatement.setInt(6, tempCourse.getCredits());
                
                insertStatement.executeUpdate();
                
                conn.commit();
                committed = true;
            } finally {
                if(!committed) {
                    conn.rollback();
                }
            }
        } finally {
            conn.close();
        }
        
        tempCourse = new Course();
        completedCourses = generateCompletedCourses();
        
        return null;
    }
    
    public String delete(Course course) throws SQLException {
        if (ds == null) {
            throw new SQLException("ds is null; Can't get data source");
        }

        Connection conn = ds.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }
        
        try {
            conn.setAutoCommit(false);
            boolean committed = false;
            try {
                PreparedStatement deleteStatement = conn.prepareStatement("delete from completed_course where"
                        + " com_course_id = ?");
                deleteStatement.setInt(1, course.getComId());
                
                deleteStatement.executeUpdate();
                conn.commit();
                committed = true;
            } finally {
                if(!committed) {
                    conn.rollback();
                }
            }
        } finally {
            conn.close();
        }
        
        completedCourses = generateCompletedCourses();
        
        return null;
    }
    
    public ArrayList<Course> generateCompletedCourses() throws SQLException {
        if(ds == null) {
            throw new SQLException("Cannot get DataSource");
        }
        
        Connection conn = ds.getConnection();
        if(conn == null) {
            throw new SQLException("Cannot get Connection");
        }
        
        completedCourses = new ArrayList<>();
        
        try {
            PreparedStatement ps = conn.prepareStatement("select com_course_id, course_prefix, course_id, course_name, semester, credit_hours"
                    + " from completed_course where student_id = ?");
            
            ps.setInt(1, studentId);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Course c = new Course();
                c.setComId(result.getInt("com_course_id"));
                c.setPrefix(result.getString("course_prefix"));
                c.setId(result.getInt("course_id"));
                c.setName(result.getString("course_name"));
                c.setSemester(result.getString("semester"));
                c.setCredits(result.getInt("credit_hours"));
                completedCourses.add(c);
            }
        } finally {
            conn.close();
        }
        
        return completedCourses;
    }
    
    public String registerStudent() throws SQLException {
        if(ds == null) {
            throw new SQLException("Cannot get DataSource");
        }
        
        Connection conn = ds.getConnection();
        if(conn == null) {
            throw new SQLException("Cannot get Connection");
        }
        
        try {
            conn.setAutoCommit(false);
            boolean committed = false;
            try {
                PreparedStatement registerQuery = conn.prepareStatement(
                        "insert into user_table (user_id, email, password, first_name, last_name)"
                                + " values (?, ?, ?, ?, ?)");
                registerQuery.setInt(1, studentId);
                registerQuery.setString(2, email);
                registerQuery.setString(3, SHA256Encrypt.encrypt(password));
                registerQuery.setString(4, firstName);
                registerQuery.setString(5, lastName);
                
                registerQuery.executeUpdate();
                conn.commit();
                
                registerQuery = conn.prepareStatement("insert into student (student_id, major) values (?, ?)");
                registerQuery.setInt(1, studentId);
                registerQuery.setString(2, major);
                registerQuery.executeUpdate();
                conn.commit();
                
                registerQuery = conn.prepareStatement("insert into user_group(group_name, user_email) values (?, ?)");
                registerQuery.setString(1, "studentgroup");
                registerQuery.setString(2, email);
                
                registerQuery.executeUpdate();
                conn.commit();
                committed = true;
            } finally {
                if(!committed) {
                    conn.rollback();
                }
            }
        } finally {
            conn.close();
        }
        
        sendVerification();
        completedCourses = generateCompletedCourses();
        
        return "index";
    }
	
    public void sendVerification() {

        final String username = "ucoadvisign@gmail.com";
        final String password = "advisignisgreat";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });

        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("ucoadvisign@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("kstubblefield1@uco.edu"));
                message.setSubject("Advisign Registration");
                message.setText("Thanks for signing up with Advisign! This email lets you know your account has been created!");

                Transport.send(message);

                System.out.println("Done");

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public ArrayList<Course> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(ArrayList<Course> completedCourses) {
        this.completedCourses = completedCourses;
    }

    public Course getTempCourse() {
        return tempCourse;
    }

    public void setTempCourse(Course tempCourse) {
        this.tempCourse = tempCourse;
    }
    
}
