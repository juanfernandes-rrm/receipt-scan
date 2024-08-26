package br.ufpr.tads.repceiptscan.repository;

import br.ufpr.tads.repceiptscan.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID>{
}
