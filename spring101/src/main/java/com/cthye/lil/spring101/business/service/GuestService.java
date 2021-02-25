package com.cthye.lil.spring101.business.service;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.repository.GuestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestService {
    private final GuestDetailRepository guestRepository;

    @Autowired
    public GuestService(GuestDetailRepository guestRepository) {
        this.guestRepository = guestRepository;
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

        guestDetails.sort((o1, o2) -> {
            if (o1.getFull_name().equals(o2.getFull_name())) {
                return Long.compare(o1.getGuest_id(), o2.getGuest_id());
            }
            return o1.getFull_name().compareTo(o2.getFull_name());
        });
        return guestDetails;
    }

}
