package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.UsrTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsrTransactionRepository extends JpaRepository<UsrTransaction, String> {

    Optional<UsrTransaction> findByToken(String token);

}
