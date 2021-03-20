package com.cthye.lil.spring101.data.repository;

import com.cthye.lil.spring101.data.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String userName);
}
