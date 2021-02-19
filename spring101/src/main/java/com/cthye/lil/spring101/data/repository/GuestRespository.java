package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRespository extends CrudRepository<Guest, Long> {
}
