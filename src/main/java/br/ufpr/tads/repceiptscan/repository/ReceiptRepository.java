package br.ufpr.tads.repceiptscan.repository;

import br.ufpr.tads.repceiptscan.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
    Optional<Receipt> findByAccessKey(String accessKey);

    Page<Receipt> findByScannedBy(UUID keycloakId, Pageable pageable);
}
