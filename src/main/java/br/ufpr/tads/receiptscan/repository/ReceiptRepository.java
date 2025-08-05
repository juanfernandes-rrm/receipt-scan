package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

    Optional<Receipt> findByAccessKey(String accessKey);

}
