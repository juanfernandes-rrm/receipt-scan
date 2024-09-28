package br.ufpr.tads.repceiptscan.repository;

import br.ufpr.tads.repceiptscan.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findByCNPJ(String cnpj);
}
