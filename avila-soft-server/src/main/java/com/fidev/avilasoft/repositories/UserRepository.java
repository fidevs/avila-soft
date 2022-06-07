package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
