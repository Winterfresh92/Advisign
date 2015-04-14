package edu.uco.advisign;

import java.util.ArrayList;
import java.util.Date;

public class AdvisementSlot {
    
    private int id;
    private Date date;
    private Date startTime;
    private Date endTime;
    private int numberOfSlots;
    private int interval;
    private ArrayList<Appointment> appointments;
    
    public AdvisementSlot() {
    }
    
    public ArrayList<Appointment> generateAppointments() {
        appointments = new ArrayList<>();
        Date currentTime = startTime;
        return appointments;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
