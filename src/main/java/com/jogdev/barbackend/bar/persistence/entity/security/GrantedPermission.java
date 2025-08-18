package com.jogdev.barbackend.bar.persistence.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GrantedPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "granted_permission_id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;
}