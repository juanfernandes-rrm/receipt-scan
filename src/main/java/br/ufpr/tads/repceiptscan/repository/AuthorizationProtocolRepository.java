package br.ufpr.tads.repceiptscan.repository;

import br.ufpr.tads.repceiptscan.model.AuthorizationProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorizationProtocolRepository extends JpaRepository<AuthorizationProtocol, UUID> {
}
