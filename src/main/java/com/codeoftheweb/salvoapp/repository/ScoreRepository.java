package com.codeoftheweb.salvoapp.repository;

import com.codeoftheweb.salvoapp.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ScoreRepository extends JpaRepository <Score, Long> {
}
