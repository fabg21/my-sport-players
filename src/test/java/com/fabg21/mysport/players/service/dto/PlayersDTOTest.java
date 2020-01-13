package com.fabg21.mysport.players.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fabg21.mysport.players.web.rest.TestUtil;

public class PlayersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayersDTO.class);
        PlayersDTO playersDTO1 = new PlayersDTO();
        playersDTO1.setId(1L);
        PlayersDTO playersDTO2 = new PlayersDTO();
        assertThat(playersDTO1).isNotEqualTo(playersDTO2);
        playersDTO2.setId(playersDTO1.getId());
        assertThat(playersDTO1).isEqualTo(playersDTO2);
        playersDTO2.setId(2L);
        assertThat(playersDTO1).isNotEqualTo(playersDTO2);
        playersDTO1.setId(null);
        assertThat(playersDTO1).isNotEqualTo(playersDTO2);
    }
}
