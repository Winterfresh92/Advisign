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
public class AdvisorBean implements Serializable {
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private String verificationCode;
    private Account tempAccount;
    private ArrayList<Account> secretaryAccounts;

    @Resource(name = "jdbc/jdbcAdv")
    private DataSource ds;
    
    public AdvisorBean() {
    }
    
    @PostConstruct
    public void init() {
        tempAccount = new Account();
        try{
            secretaryAccounts = generateSecretaryAccounts();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    //Begin
    public String saveAccount(Account account) throws SQLException {
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
                PreparedStatement updateStatement = conn.prepareStatement("update advisor set lastName=? where advisor_id=?");
                updateStatement.setString(1, account.getLastName());
                updateStatement.setInt(2, account.getAdvisorID());
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
                
        account.setEditable(false);
        secretaryAccounts = generateSecretaryAccounts();
        
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
                PreparedStatement insertStatement = conn.prepareStatement("insert into advisor(lastName) values(?)");
                insertStatement.setString(1, tempAccount.getLastName());
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
        
        tempAccount = new Account();
        secretaryAccounts = generateSecretaryAccounts();
        
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
                PreparedStatement deleteStatement = conn.prepareStatement("delete from advisor where advisor_id=?");
                //deleteStatement.setInt(1, account.getAdvisorID());
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
        
        secretaryAccounts = generateSecretaryAccounts();
        
        return null;
    }
    //End
    
    public ArrayList<Account> generateSecretaryAccounts() throws SQLException{
        if(ds == null) {
            throw new SQLException("Cannot get DataSource");
        }
        
        Connection conn = ds.getConnection();
        if(conn == null) {
            throw new SQLException("Cannot get Connection");
        }
        
        secretaryAccounts = new ArrayList<>();
        
        try {
            PreparedStatement ps = conn.prepareStatement("select *");
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                Account a = new Account();
                secretaryAccounts.add(a);
            }
        } finally {
            conn.close();
        }
        
        return secretaryAccounts;
    }
    
    public String editAccount(Account account){
        account.setEditable(true);
        return null;
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
    
    public ArrayList<Account> getSecretaryAccounts(){
        return secretaryAccounts;
    }
    
    public void setSecretaryAccounts(ArrayList<Account> secretaryAccounts){
        this.secretaryAccounts = secretaryAccounts;
    }
    
    public Account getTempAccount(){
        return tempAccount;
    }
    
    public void setTempAccount(Account tempAccount){
        this.tempAccount = tempAccount;
    }
}
