package com.cthye.lil.spring101.api;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.domain.RoomReservation;
import com.cthye.lil.spring101.business.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestDetailApiController {
    private final GuestService guestService;

    @Autowired
    public GuestDetailApiController(GuestService guestService) {
        super();
        this.guestService = guestService;
    }

    @GetMapping("/guests")
    public List<GuestDetail> getAllGuests(){
        return this.guestService.getAllGuests();
    }

    @GetMapping("/guests/{guestId}")
    public EntityModel<RoomReservation> getSpecificReservation(@PathVariable("guestId") String guestID){
        return null;
    }
}
