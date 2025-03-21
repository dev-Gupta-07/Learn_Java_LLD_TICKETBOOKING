package org.example.entities;

import java.util.List;

public class User {
    private String name;

    public User() {
    }

    public User(String name, String password,String hashedPassword,List<Ticket>ticketBooked,String userId) {
        this.password = password;
        this.name=name;
        this.hashedPassword=hashedPassword;
        this.ticketBooked=ticketBooked;
        this.userId=userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTicketBooked() {
        return ticketBooked;
    }

    public void setTicketBooked(List<Ticket> ticketBooked) {
        this.ticketBooked = ticketBooked;
    }
    public void printTicketBooked(){
        for(int i=0;i<ticketBooked.size();i++){
            System.out.println(ticketBooked.get(i).getTicketInfo());
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String hashedPassword;
    private List<Ticket> ticketBooked;
    private String userId;
}
