package com.jogdev.barbackend.bar.persistence.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="role_id")
    private int id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<GrantedPermission> permissions;

    public enum RoleEnum {
        ADMIN, STOCK_MANAGER, SELLER
    }

    @Override
    public String getAuthority() {
        if (name == null) return null;

        return "ROLE_" + name.name();
    }
}