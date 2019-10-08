package com.codeoftheweb.salvoapp.repository;

import com.codeoftheweb.salvoapp.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository <Ship, Long> {
}
