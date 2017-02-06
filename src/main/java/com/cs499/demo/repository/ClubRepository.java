package com.cs499.demo.repository;

import com.cs499.demo.domain.Club;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Club entity.
 */
@SuppressWarnings("unused")
public interface ClubRepository extends JpaRepository<Club,Long> {

}
