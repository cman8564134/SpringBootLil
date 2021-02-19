package com.cthye.lil.reservationconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class ReservationListProcessor {
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationListProcessor.class);


    @Autowired
    public ReservationListProcessor(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    public void receiveMessage(String jsonMsg) {
        LOGGER.info("Message Received");
        try {
            RoomReservation reservation = this.objectMapper.readValue(jsonMsg, RoomReservation.class);
            LOGGER.info("Reservation made for " + reservation.getLastName());
        } catch (IOException e) {
            LOGGER.error("Exception caught ", e);
        }
    }
}
