package com.smoothstack.utopia.user.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;
    private String givenName;
    private String familyName;
    private String username;
    private String email;
    private String password;
    private String phone;

    public boolean isComplete() {
        return userRole != null && userRole.getId() > 0 && userRole.getId() < 5 && givenName != null && familyName != null && username != null && email != null && password != null && phone != null;
    }

    public void update(User user) {
        if (user.getUserRole() != null) userRole = user.getUserRole();
        if (user.getGivenName() != null) givenName = user.getGivenName();
        if (user.getFamilyName() != null) familyName = user.getFamilyName();
        if (user.getUsername() != null) username = user.getUsername();
        if (user.getEmail() != null) email = user.getEmail();
        if (user.getPassword() != null) password = user.getPassword();
        if (user.getPhone() != null) phone = user.getPhone();
    }
}
