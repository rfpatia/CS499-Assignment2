package com.cs499.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.demo.domain.Soccerplayer;

import com.cs499.demo.repository.SoccerplayerRepository;
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
 * REST controller for managing Soccerplayer.
 */
@RestController
@RequestMapping("/api")
public class SoccerplayerResource {

    private final Logger log = LoggerFactory.getLogger(SoccerplayerResource.class);
        
    @Inject
    private SoccerplayerRepository soccerplayerRepository;

    /**
     * POST  /soccerplayers : Create a new soccerplayer.
     *
     * @param soccerplayer the soccerplayer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new soccerplayer, or with status 400 (Bad Request) if the soccerplayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/soccerplayers")
    @Timed
    public ResponseEntity<Soccerplayer> createSoccerplayer(@Valid @RequestBody Soccerplayer soccerplayer) throws URISyntaxException {
        log.debug("REST request to save Soccerplayer : {}", soccerplayer);
        if (soccerplayer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("soccerplayer", "idexists", "A new soccerplayer cannot already have an ID")).body(null);
        }
        Soccerplayer result = soccerplayerRepository.save(soccerplayer);
        return ResponseEntity.created(new URI("/api/soccerplayers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("soccerplayer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /soccerplayers : Updates an existing soccerplayer.
     *
     * @param soccerplayer the soccerplayer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated soccerplayer,
     * or with status 400 (Bad Request) if the soccerplayer is not valid,
     * or with status 500 (Internal Server Error) if the soccerplayer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/soccerplayers")
    @Timed
    public ResponseEntity<Soccerplayer> updateSoccerplayer(@Valid @RequestBody Soccerplayer soccerplayer) throws URISyntaxException {
        log.debug("REST request to update Soccerplayer : {}", soccerplayer);
        if (soccerplayer.getId() == null) {
            return createSoccerplayer(soccerplayer);
        }
        Soccerplayer result = soccerplayerRepository.save(soccerplayer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("soccerplayer", soccerplayer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /soccerplayers : get all the soccerplayers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of soccerplayers in body
     */
    @GetMapping("/soccerplayers")
    @Timed
    public List<Soccerplayer> getAllSoccerplayers() {
        log.debug("REST request to get all Soccerplayers");
        List<Soccerplayer> soccerplayers = soccerplayerRepository.findAll();
        return soccerplayers;
    }

    /**
     * GET  /soccerplayers/:id : get the "id" soccerplayer.
     *
     * @param id the id of the soccerplayer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the soccerplayer, or with status 404 (Not Found)
     */
    @GetMapping("/soccerplayers/{id}")
    @Timed
    public ResponseEntity<Soccerplayer> getSoccerplayer(@PathVariable Long id) {
        log.debug("REST request to get Soccerplayer : {}", id);
        Soccerplayer soccerplayer = soccerplayerRepository.findOne(id);
        return Optional.ofNullable(soccerplayer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /soccerplayers/:id : delete the "id" soccerplayer.
     *
     * @param id the id of the soccerplayer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/soccerplayers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSoccerplayer(@PathVariable Long id) {
        log.debug("REST request to delete Soccerplayer : {}", id);
        soccerplayerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("soccerplayer", id.toString())).build();
    }

}
