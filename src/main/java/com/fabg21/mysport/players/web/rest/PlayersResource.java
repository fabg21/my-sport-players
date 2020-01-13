package com.fabg21.mysport.players.web.rest;

import com.fabg21.mysport.players.service.PlayersService;
import com.fabg21.mysport.players.web.rest.errors.BadRequestAlertException;
import com.fabg21.mysport.players.service.dto.PlayersDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fabg21.mysport.players.domain.Players}.
 */
@RestController
@RequestMapping("/api")
public class PlayersResource {

    private final Logger log = LoggerFactory.getLogger(PlayersResource.class);

    private static final String ENTITY_NAME = "mySportPlayersPlayers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayersService playersService;

    public PlayersResource(PlayersService playersService) {
        this.playersService = playersService;
    }

    /**
     * {@code POST  /players} : Create a new players.
     *
     * @param playersDTO the playersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playersDTO, or with status {@code 400 (Bad Request)} if the players has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/players")
    public ResponseEntity<PlayersDTO> createPlayers(@Valid @RequestBody PlayersDTO playersDTO) throws URISyntaxException {
        log.debug("REST request to save Players : {}", playersDTO);
        if (playersDTO.getId() != null) {
            throw new BadRequestAlertException("A new players cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayersDTO result = playersService.save(playersDTO);
        return ResponseEntity.created(new URI("/api/players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /players} : Updates an existing players.
     *
     * @param playersDTO the playersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playersDTO,
     * or with status {@code 400 (Bad Request)} if the playersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/players")
    public ResponseEntity<PlayersDTO> updatePlayers(@Valid @RequestBody PlayersDTO playersDTO) throws URISyntaxException {
        log.debug("REST request to update Players : {}", playersDTO);
        if (playersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayersDTO result = playersService.save(playersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /players} : get all the players.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of players in body.
     */
    @GetMapping("/players")
    public List<PlayersDTO> getAllPlayers() {
        log.debug("REST request to get all Players");
        return playersService.findAll();
    }

    /**
     * {@code GET  /players/:id} : get the "id" players.
     *
     * @param id the id of the playersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/players/{id}")
    public ResponseEntity<PlayersDTO> getPlayers(@PathVariable Long id) {
        log.debug("REST request to get Players : {}", id);
        Optional<PlayersDTO> playersDTO = playersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playersDTO);
    }

    /**
     * {@code DELETE  /players/:id} : delete the "id" players.
     *
     * @param id the id of the playersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayers(@PathVariable Long id) {
        log.debug("REST request to delete Players : {}", id);
        playersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
