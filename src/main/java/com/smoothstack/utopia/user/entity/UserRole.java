package com.smoothstack.utopia.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "user_role")
@Entity
public class UserRole {
    @Id
    private int id;
    private String name;

    public UserRole(int id) {
        this.id = id;
    }
}
