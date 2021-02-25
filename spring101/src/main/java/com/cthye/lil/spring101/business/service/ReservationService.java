package com.cthye.lil.spring101.business.service;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.domain.RoomReservation;
import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.entity.Reservation;
import com.cthye.lil.spring101.data.entity.Room;
import com.cthye.lil.spring101.data.repository.GuestDetailRepository;
import com.cthye.lil.spring101.data.repository.ReservationRepository;
import com.cthye.lil.spring101.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestDetailRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestDetailRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<GuestDetail> getAllGuests() {

        Iterable<Guest> guests = this.guestRepository.findAll();
        List<GuestDetail> guestDetails = new ArrayList<>();
        guests.forEach(guest -> {
            GuestDetail guestDetail = new GuestDetail();
            guestDetail.setGuest_id(guest.getId());
            guestDetail.setEmail(guest.getEmail());
            guestDetail.setFull_name(guest.getLast_name() + ' ' + guest.getFirst_name());
            guestDetail.setPhone_number(guest.getPhone_number());
            guestDetails.add(guestDetail);
        });

        guestDetails.sort(new Comparator<GuestDetail>() {
            @Override
            public int compare(GuestDetail o1, GuestDetail o2) {
                if (o1.getFull_name().equals(o2.getFull_name())) {
                    return Long.compare(o1.getGuest_id(), o2.getGuest_id());
                }
                return o1.getFull_name().compareTo(o2.getFull_name());
            }
        });
        return guestDetails;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getId()).get();
            roomReservation.setFirstName(guest.getFirst_name());
            roomReservation.setLastName(guest.getLast_name());
            roomReservation.setGuestId(guest.getId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if (o1.getRoomName() == o2.getRoomName()) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        return roomReservations;
    }
}