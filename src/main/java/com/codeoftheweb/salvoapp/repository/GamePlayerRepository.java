package com.codeoftheweb.salvoapp.repository;

import com.codeoftheweb.salvoapp.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {
}
