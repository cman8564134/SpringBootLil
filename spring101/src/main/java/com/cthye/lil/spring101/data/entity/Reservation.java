package com.cthye.lil.spring101.data.entity;

import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @Column(name = "RESERVATION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_reservation_id_seq")
    @SequenceGenerator(name = "reservation_reservation_id_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "ROOM_ID")
    @NotBlank(message = "FK room_id constraint cannot be empty")
    @RestResource(path = "rooms", rel="rooms")
    private Room room;

    @OneToOne
    @JoinColumn(name = "GUEST_ID")
    @NotBlank(message = "FK guest_id constraint cannot be empty")
    @RestResource(path = "guests", rel="guests")
    private Guest guest;

    @Column(name = "RES_DATE")
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room_id) {
        this.room = room_id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest_id) {
        this.guest = guest_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
