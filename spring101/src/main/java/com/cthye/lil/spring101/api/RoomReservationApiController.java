package com.cthye.lil.spring101.api;

import com.cthye.lil.spring101.aspect.LogMethodCount;
import com.cthye.lil.spring101.business.domain.RoomReservation;
import com.cthye.lil.spring101.business.service.ReservationService;
import com.cthye.lil.spring101.web.DateUtils;
import com.frankmoley.landon.aop.servicelogging.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomReservationApiController {
    private final ReservationService reservationService;

    @Autowired
    public RoomReservationApiController(ReservationService reservationService) {
        super();
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    @Timed
    @LogMethodCount
    public List<RoomReservation> getReservations(@RequestParam(value = "date", required = false) String dateString) {
        Date date = DateUtils.createDateFromDateString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }
}
