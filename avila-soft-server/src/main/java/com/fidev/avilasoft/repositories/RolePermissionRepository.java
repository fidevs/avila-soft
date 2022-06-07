package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.RolePermission;
import com.fidev.avilasoft.entities.RolePermissionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionPK> {
}
