package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.Reservation;
import com.cthye.lil.spring101.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Iterable<Reservation> findReservationByDate(Date date);

    //build method using Spring Data Query
    @Query("SELECT r FROM Reservation r WHERE r.date = ?1 ORDER BY r.id")
    Iterable<Reservation> lookupReservationByDate(Date date);

    Iterable<Reservation> findReservationByRoomRoomName (@Param("room_ID")String roomName);

    @Override
    @RestResource(exported = false)
    <S extends Reservation> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Reservation> S saveAndFlush(S s);

    @Override
    @RestResource(exported = false)
    void deleteInBatch(Iterable<Reservation> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Reservation> S save(S s);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Reservation reservation);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Reservation> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
