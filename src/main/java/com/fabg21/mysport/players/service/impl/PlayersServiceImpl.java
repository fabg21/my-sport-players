package com.fabg21.mysport.players.service.impl;

import com.fabg21.mysport.players.service.PlayersService;
import com.fabg21.mysport.players.domain.Players;
import com.fabg21.mysport.players.repository.PlayersRepository;
import com.fabg21.mysport.players.service.dto.PlayersDTO;
import com.fabg21.mysport.players.service.mapper.PlayersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Players}.
 */
@Service
@Transactional
public class PlayersServiceImpl implements PlayersService {

    private final Logger log = LoggerFactory.getLogger(PlayersServiceImpl.class);

    private final PlayersRepository playersRepository;

    private final PlayersMapper playersMapper;

    public PlayersServiceImpl(PlayersRepository playersRepository, PlayersMapper playersMapper) {
        this.playersRepository = playersRepository;
        this.playersMapper = playersMapper;
    }

    /**
     * Save a players.
     *
     * @param playersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlayersDTO save(PlayersDTO playersDTO) {
        log.debug("Request to save Players : {}", playersDTO);
        Players players = playersMapper.toEntity(playersDTO);
        players = playersRepository.save(players);
        return playersMapper.toDto(players);
    }

    /**
     * Get all the players.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlayersDTO> findAll() {
        log.debug("Request to get all Players");
        return playersRepository.findAll().stream()
            .map(playersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one players by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlayersDTO> findOne(Long id) {
        log.debug("Request to get Players : {}", id);
        return playersRepository.findById(id)
            .map(playersMapper::toDto);
    }

    /**
     * Delete the players by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Players : {}", id);
        playersRepository.deleteById(id);
    }
}
