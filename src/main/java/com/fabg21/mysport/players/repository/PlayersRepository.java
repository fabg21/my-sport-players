package com.fabg21.mysport.players.repository;
import com.fabg21.mysport.players.domain.Players;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Players entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayersRepository extends JpaRepository<Players, Long> {

}
