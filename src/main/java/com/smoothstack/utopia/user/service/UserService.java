package com.smoothstack.utopia.user.service;

import com.smoothstack.utopia.user.entity.User;
import com.smoothstack.utopia.user.entity.UserRole;
import com.smoothstack.utopia.user.repository.UserRepository;
import com.smoothstack.utopia.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> addUser(User user) {
        if (user.isComplete()) {
            UserRole ur = userRoleRepository.getById(user.getUserRole().getId());
            return Optional.of(userRepository.save(user));
        } else {
            System.out.println(user);
            return Optional.empty();
        }
    }

    public Optional<User> updateUserById(int id, User user) {
        User u = userRepository.getById(id);
        u.update(user);
        if (u.isComplete()) {
            return Optional.of(userRepository.save(u));
        } else return Optional.empty();
    }
}
