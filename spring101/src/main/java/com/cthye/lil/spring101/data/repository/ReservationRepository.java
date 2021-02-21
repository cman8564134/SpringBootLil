package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Iterable<Reservation> findReservationByDate(Date date);

    //build method using Spring Data Query
    @Query("SELECT r FROM Reservation r WHERE r.date = ?1 ORDER BY r.id")
    Iterable<Reservation> lookupReservationByDate(Date date);
}
