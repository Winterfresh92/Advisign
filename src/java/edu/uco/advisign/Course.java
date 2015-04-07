package edu.uco.advisign;

import java.util.ArrayList;

public class Course {
    
    private int comId;
    private String prefix;
    private int id;
    private String name;
    private String semester;
    private int credits;
    private boolean editable;
    private ArrayList<Course> prereqs;
    
    public Course() {
        
    }
    
    public Course(String prefix, int id, String name) {
        this.prefix = prefix;
        this.id = id;
        this.name = name;
        editable = false;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getPrereqs() {
        return prereqs;
    }

    public void setPrereqs(ArrayList<Course> prereqs) {
        this.prereqs = prereqs;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }
    
}
