package com.cs499.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.demo.domain.Club;

import com.cs499.demo.repository.ClubRepository;
import com.cs499.demo.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Club.
 */
@RestController
@RequestMapping("/api")
public class ClubResource {

    private final Logger log = LoggerFactory.getLogger(ClubResource.class);
        
    @Inject
    private ClubRepository clubRepository;

    /**
     * POST  /clubs : Create a new club.
     *
     * @param club the club to create
     * @return the ResponseEntity with status 201 (Created) and with body the new club, or with status 400 (Bad Request) if the club has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clubs")
    @Timed
    public ResponseEntity<Club> createClub(@Valid @RequestBody Club club) throws URISyntaxException {
        log.debug("REST request to save Club : {}", club);
        if (club.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("club", "idexists", "A new club cannot already have an ID")).body(null);
        }
        Club result = clubRepository.save(club);
        return ResponseEntity.created(new URI("/api/clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("club", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clubs : Updates an existing club.
     *
     * @param club the club to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated club,
     * or with status 400 (Bad Request) if the club is not valid,
     * or with status 500 (Internal Server Error) if the club couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clubs")
    @Timed
    public ResponseEntity<Club> updateClub(@Valid @RequestBody Club club) throws URISyntaxException {
        log.debug("REST request to update Club : {}", club);
        if (club.getId() == null) {
            return createClub(club);
        }
        Club result = clubRepository.save(club);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("club", club.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clubs : get all the clubs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clubs in body
     */
    @GetMapping("/clubs")
    @Timed
    public List<Club> getAllClubs() {
        log.debug("REST request to get all Clubs");
        List<Club> clubs = clubRepository.findAll();
        return clubs;
    }

    /**
     * GET  /clubs/:id : get the "id" club.
     *
     * @param id the id of the club to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the club, or with status 404 (Not Found)
     */
    @GetMapping("/clubs/{id}")
    @Timed
    public ResponseEntity<Club> getClub(@PathVariable Long id) {
        log.debug("REST request to get Club : {}", id);
        Club club = clubRepository.findOne(id);
        return Optional.ofNullable(club)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clubs/:id : delete the "id" club.
     *
     * @param id the id of the club to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clubs/{id}")
    @Timed
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        log.debug("REST request to delete Club : {}", id);
        clubRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("club", id.toString())).build();
    }

}
