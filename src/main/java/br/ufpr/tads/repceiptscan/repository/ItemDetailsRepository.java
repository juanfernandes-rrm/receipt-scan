package br.ufpr.tads.repceiptscan.repository;

import br.ufpr.tads.repceiptscan.model.Item;
import br.ufpr.tads.repceiptscan.model.ItemDetails;
import br.ufpr.tads.repceiptscan.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemDetailsRepository extends JpaRepository<ItemDetails, UUID> {
    Optional<ItemDetails> findByItemAndReceipt(Item item, Receipt receipt);
}
