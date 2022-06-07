package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConfigRepository extends JpaRepository<UserConfig, String> {
}
