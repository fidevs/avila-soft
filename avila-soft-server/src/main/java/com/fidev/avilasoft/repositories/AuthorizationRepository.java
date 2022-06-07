package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, String> {
}
