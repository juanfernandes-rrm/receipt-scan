package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.Item;
import br.ufpr.tads.receiptscan.model.ItemDetails;
import br.ufpr.tads.receiptscan.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemDetailsRepository extends JpaRepository<ItemDetails, UUID> {
    Optional<ItemDetails> findByItemAndReceipt(Item item, Receipt receipt);
}
