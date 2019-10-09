package com.codeoftheweb.salvoapp.repository;

import com.codeoftheweb.salvoapp.model.Salvo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalvoRepository extends JpaRepository<Salvo, Long> {
}
