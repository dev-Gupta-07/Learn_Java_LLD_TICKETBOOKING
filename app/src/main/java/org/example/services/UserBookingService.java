package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static final String USERS_PATH="app/src/main/java/org/example/localDb/users.json";
    private ObjectMapper objectMapper=new ObjectMapper();
    public UserBookingService(User user1)throws IOException {
        this.user = user1;
        File users=new File(USERS_PATH);
        userList=objectMapper.readValue(users,new TypeReference<List<User>>(){});

    }
    public UserBookingService()throws IOException{
       try {
           File users = new File(USERS_PATH);
           userList = objectMapper.readValue(users, new TypeReference<List<User>>() {
           });
       }catch(IOException e){
           e.printStackTrace();
       }

    }
    //List<Integer>l=Arrays.asList(1,2,3);
    //l.stream().map(e->e*2).collect(Collectors.toList())
    //same for filter functional interface Predicate  apply
    public Boolean loginUser(){
        Optional<User>foundUser=userList.stream().filter(user1->{
              return user1.getName().equalsIgnoreCase(user.getName())&& UserServiceUtil.checkPassword(user.getPassword(),user1.getPassword());
        }).findFirst();
        return foundUser.isPresent();
    }
    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;

        }catch(IOException ex){
            return Boolean.FALSE;
        }
    }
    private void saveUserListToFile() throws IOException {
        File users=new File(USERS_PATH);
        objectMapper.writeValue(users,userList);

    }
    public void fetchBookings(){
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if(userFetched.isPresent()){
            userFetched.get().printTicketBooked();
        }
    }
//    public void cancelBooking(String ticketId){
//        user.getTicketBooked().stream().filter(e)
//    }
public List<Train> getTrains(String source, String destination){
    try{
        TrainService trainService = new TrainService();
        return trainService.searchTrains(source, destination);
    }catch(IOException ex){
        return new ArrayList<>();
    }
}
    public Boolean cancelBooking(String ticketId){

        Scanner s = new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel");
        ticketId = s.next();

        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return Boolean.FALSE;
        }

        String finalTicketId1 = ticketId;
        boolean removed = user.getTicketBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

        String finalTicketId = ticketId;
        user.getTicketBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }
    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }







}
