package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Iterable<Room> findByRoomId(@Param(("RoomID")) Long RoomID);
}

