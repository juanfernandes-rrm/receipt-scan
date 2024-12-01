package br.ufpr.tads.receiptscan.repository;

import br.ufpr.tads.receiptscan.dto.response.UserStatistics;
import br.ufpr.tads.receiptscan.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
    Optional<Receipt> findByAccessKey(String accessKey);

    Page<Receipt> findByScannedBy(UUID keycloakId, Pageable pageable);

    @Query("""
    SELECT new br.ufpr.tads.receiptscan.dto.response.UserStatistics(
        r.scannedBy,
        COUNT(r.id) AS totalReceipts,
        COALESCE(SUM(r.totalItems), 0) AS totalProducts
    )
    FROM Receipt r
    GROUP BY r.scannedBy
    """)
    Slice<UserStatistics> findUserStatistics(Pageable pageable);

}
