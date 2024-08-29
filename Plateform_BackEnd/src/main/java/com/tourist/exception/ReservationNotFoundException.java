package com.tourist.exception;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException (String msg){
        super("Reservation not found");
    }
}
