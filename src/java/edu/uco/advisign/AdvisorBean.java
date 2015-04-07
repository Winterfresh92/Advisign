package edu.uco.advisign;

import java.io.Serializable;
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
public class AdvisorBean implements Serializable {
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private String verificationCode;

    @Resource(name = "jdbc/Adv")
    private DataSource ds;
    
    public AdvisorBean() {
    }
    
    @PostConstruct
    public void init() {
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
}
