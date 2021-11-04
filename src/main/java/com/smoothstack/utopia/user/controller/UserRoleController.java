package com.smoothstack.utopia.user.controller;

import com.smoothstack.utopia.user.entity.UserRole;
import com.smoothstack.utopia.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/all")
    public List<UserRole> getUserRoles() {
        return userRoleService.getAllUserRoles();
    }
}
