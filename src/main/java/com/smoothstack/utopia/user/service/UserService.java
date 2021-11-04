package com.smoothstack.utopia.user.service;

import com.smoothstack.utopia.user.entity.User;
import com.smoothstack.utopia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> addUser(User user) {
        if (user.isComplete()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return Optional.of(userRepository.saveAndFlush(user));
        } else {
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

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}
