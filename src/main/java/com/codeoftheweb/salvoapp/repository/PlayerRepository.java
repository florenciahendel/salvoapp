package com.codeoftheweb.salvoapp.repository;

import com.codeoftheweb.salvoapp.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository <Player, Long>{
}
