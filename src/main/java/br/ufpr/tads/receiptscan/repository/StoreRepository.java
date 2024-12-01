package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findByCNPJ(String cnpj);
}
