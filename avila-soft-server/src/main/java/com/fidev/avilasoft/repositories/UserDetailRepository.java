package com.fidev.avilasoft.repositories;

import com.fidev.avilasoft.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
}
