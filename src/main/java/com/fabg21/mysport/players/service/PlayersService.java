package com.fabg21.mysport.players.service;

import com.fabg21.mysport.players.service.dto.PlayersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fabg21.mysport.players.domain.Players}.
 */
public interface PlayersService {

    /**
     * Save a players.
     *
     * @param playersDTO the entity to save.
     * @return the persisted entity.
     */
    PlayersDTO save(PlayersDTO playersDTO);

    /**
     * Get all the players.
     *
     * @return the list of entities.
     */
    List<PlayersDTO> findAll();


    /**
     * Get the "id" players.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlayersDTO> findOne(Long id);

    /**
     * Delete the "id" players.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
