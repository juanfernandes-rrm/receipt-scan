package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.City;
import br.ufpr.tads.receiptscan.model.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID>{

    Optional<City> findByNameAndState(String name, StateEnum state);

}
