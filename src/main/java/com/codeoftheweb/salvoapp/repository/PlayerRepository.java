package com.codeoftheweb.salvoapp.repository;

import com.codeoftheweb.salvoapp.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository <Player, Long>{
   // List <Player>findByUserName(String username);

    Player findByUserName(@Param("userName") String userName);
    Player findOneByUserName (@Param("userName") String userName);

    Player findPlayerByUserName(String inputName);
}
