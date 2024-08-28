package br.ufpr.tads.repceiptscan.repository;

import br.ufpr.tads.repceiptscan.model.City;
import br.ufpr.tads.repceiptscan.model.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID>{

    Optional<City> findByNameAndState(String name, StateEnum state);

}
