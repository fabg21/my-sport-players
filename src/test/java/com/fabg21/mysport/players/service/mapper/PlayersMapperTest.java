package com.fabg21.mysport.players.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PlayersMapperTest {

    private PlayersMapper playersMapper;

    @BeforeEach
    public void setUp() {
        playersMapper = new PlayersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(playersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(playersMapper.fromId(null)).isNull();
    }
}
