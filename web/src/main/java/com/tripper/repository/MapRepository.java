package com.tripper.repository;

import com.tripper.domain.map.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Route, Long> {
}
