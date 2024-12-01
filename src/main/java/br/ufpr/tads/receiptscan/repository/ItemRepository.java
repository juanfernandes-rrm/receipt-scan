package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    Optional<Item> findByCode(String code);
}
