package com.smoothstack.utopia.user.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Getter
@Setter
@Table(name = "user_role")
@Entity
public class UserRole {
    @Id
    private int id;
    private String name;
}
