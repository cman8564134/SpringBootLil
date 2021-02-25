package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@RepositoryRestResource(collectionResourceRel = "guests", path = "guests")
public interface GuestRespository extends JpaRepository<Guest, Long> {
}
