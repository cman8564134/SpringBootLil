package com.cthye.lil.spring101.web;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.service.GuestService;
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
    private final GuestService guestService;

    @Autowired
    public GuestDetailWebController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public String getGuests(Model model) {
        List<GuestDetail> guestDetails = this.guestService.getAllGuests();
        model.addAttribute("guestsDetails", guestDetails);
        return "guests";
    }
}
