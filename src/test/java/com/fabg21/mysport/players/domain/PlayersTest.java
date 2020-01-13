package com.fabg21.mysport.players.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fabg21.mysport.players.web.rest.TestUtil;

public class PlayersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Players.class);
        Players players1 = new Players();
        players1.setId(1L);
        Players players2 = new Players();
        players2.setId(players1.getId());
        assertThat(players1).isEqualTo(players2);
        players2.setId(2L);
        assertThat(players1).isNotEqualTo(players2);
        players1.setId(null);
        assertThat(players1).isNotEqualTo(players2);
    }
}
