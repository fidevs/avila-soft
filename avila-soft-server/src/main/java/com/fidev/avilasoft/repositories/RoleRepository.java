package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
