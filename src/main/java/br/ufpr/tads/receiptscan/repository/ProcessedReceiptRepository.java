package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessedReceiptRepository extends JpaRepository<ProcessedReceipt, UUID> {

    Optional<ProcessedReceipt> findByAccessKey(String accessKey);

}
