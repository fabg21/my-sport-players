package com.fabg21.mysport.players.service.mapper;

import com.fabg21.mysport.players.domain.*;
import com.fabg21.mysport.players.service.dto.PlayersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Players} and its DTO {@link PlayersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlayersMapper extends EntityMapper<PlayersDTO, Players> {



    default Players fromId(Long id) {
        if (id == null) {
            return null;
        }
        Players players = new Players();
        players.setId(id);
        return players;
    }
}
