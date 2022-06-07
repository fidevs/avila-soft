package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.UsrTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrTransactionRepository extends JpaRepository<UsrTransaction, String> {
}
