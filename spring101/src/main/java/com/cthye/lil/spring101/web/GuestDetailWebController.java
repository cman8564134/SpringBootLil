package com.cthye.lil.spring101.web;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.domain.RoomReservation;
import com.cthye.lil.spring101.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestDetailWebController {
    private final ReservationService reservationService;

    @Autowired
    public GuestDetailWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getGuests(Model model) {
        List<GuestDetail> guestDetails = this.reservationService.getAllGuests();
        model.addAttribute("guestsDetails", guestDetails);
        return "guests";
    }
}
