package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GuestDetailRepository extends JpaRepository<Guest, Long> {
    Page<Guest> findGuestByCountry(@Param("country")String country, Pageable pageable);

}
