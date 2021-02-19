package com.cthye.lil.reservationclr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class RoomReservationPrimer implements CommandLineRunner {

    @Value("${amqp.queue.name}")
    private String queueName;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomReservationPrimer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ConfigurableApplicationContext configurableApplicationContext;
    private final ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @Autowired
    public RoomReservationPrimer(RabbitTemplate rabbitTemplate, ConfigurableApplicationContext configurableApplicationContext, ObjectMapper objectMapper) {
        super();
        this.restTemplate = new RestTemplate();
        this.rabbitTemplate = rabbitTemplate;
        this.configurableApplicationContext = configurableApplicationContext;
        this.objectMapper = objectMapper;
    }


    @Override
    public void run(String... args) throws Exception {
        String url = "http://localhost:8000/api/reservations";
        RoomReservation[] reservations = this.restTemplate.getForObject(url, RoomReservation[].class);
        Arrays.asList(reservations).forEach(reservation -> {
            LOGGER.info("Sending Message");
            try {
                String json = objectMapper.writeValueAsString(reservation);
                rabbitTemplate.convertAndSend(queueName, json);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error while sending message...", e);
            }
        });
        //exit system when all data is sent
        System.exit(SpringApplication.exit(configurableApplicationContext));

    }
}
