package com.cs499.demo.repository;

import com.cs499.demo.domain.Soccerplayer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Soccerplayer entity.
 */
@SuppressWarnings("unused")
public interface SoccerplayerRepository extends JpaRepository<Soccerplayer,Long> {

}
