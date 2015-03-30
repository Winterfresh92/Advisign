package edu.uco.advisign;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Resource(name = "jdbc/Advisign")
    private DataSource ds;
    
    public StudentBean() {
    }
    
    @PostConstruct
    public void init() {
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
                        "insert into student (first_name, last_name, password, email, student_id, major)"
                                + " values (?, ?, ?, ?, ?, ?)");
                registerQuery.setString(1, firstName);
                registerQuery.setString(2, lastName);
                registerQuery.setString(3, SHA256Encrypt.encrypt(password));
                registerQuery.setString(4, email);
                registerQuery.setInt(5, studentId);
                registerQuery.setString(6, major);
                
                registerQuery.executeUpdate();
                conn.commit();
                /*
                registerQuery = conn.prepareStatement("insert into group_info(group_name, email) values (?, ?)");
                registerQuery.setString(1, "student_group");
                registerQuery.setString(2, email);
                
                registerQuery.executeUpdate();
                conn.commit(); */
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
        
        return "/student/index";
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
    
}
