package com.ZenBookings.demoZenBookings.application.exception;
import com.ZenBookings.demoZenBookings.application.lasting.EMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ZenBookingException extends Exception {

    private final HttpStatus status;
    private final String message;

    public ZenBookingException(EMessage eMessage) {
        this.status = eMessage.getStatus();
        this.message = eMessage.getMessage();
    }

}