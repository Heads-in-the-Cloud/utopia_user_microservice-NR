package com.smoothstack.utopia.user.repository;

import com.smoothstack.utopia.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
