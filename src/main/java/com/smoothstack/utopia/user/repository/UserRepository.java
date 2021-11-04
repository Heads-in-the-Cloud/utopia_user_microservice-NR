package com.smoothstack.utopia.user.repository;

import com.smoothstack.utopia.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
