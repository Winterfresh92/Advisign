package edu.uco.advisign;

public class Account {
    private boolean editable;
    private String lastName;
    private int advisorID;
    
    public Account(){
        
    }
    
    public Account(String name){
        editable = false;
    }
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public int getAdvisorID(){
        return advisorID;
    }
    
    public void setAdvisorID(int advisorID){
        this.advisorID = advisorID;
    }
}
