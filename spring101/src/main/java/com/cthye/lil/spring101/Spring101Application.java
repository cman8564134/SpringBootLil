package com.cthye.lil.spring101;

import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.entity.Reservation;
import com.cthye.lil.spring101.data.entity.Room;
import com.cthye.lil.spring101.data.repository.GuestRespository;
import com.cthye.lil.spring101.data.repository.ReservationRepository;
import com.cthye.lil.spring101.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Spring101Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring101Application.class, args);
    }


}
