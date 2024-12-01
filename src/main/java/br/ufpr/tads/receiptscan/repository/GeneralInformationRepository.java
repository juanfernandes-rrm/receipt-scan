package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.GeneralInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeneralInformationRepository extends JpaRepository<GeneralInformation, UUID> {
}
