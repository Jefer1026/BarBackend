    package com.jogdev.barbackend.bar.persistence.entity.security;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.List;

    @Entity
    @Getter
    @Setter
    public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private int id;

        @NotNull
        private String name;

        @Column(unique = true)
        private String username;

        private String password;

        @ManyToOne
        @JoinColumn(name = "role_id")
        private Role role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            if (role == null) return new ArrayList<>();

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));

            if (role.getAuthority() == null) return authorities;
            role.getPermissions().forEach(permission -> {
                String permissionName = permission.getPermission().getName();
                authorities.add(new SimpleGrantedAuthority(permissionName));
            });
            return authorities;
        }

        @Override
        public boolean isAccountNonExpired() {
            return UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
            return UserDetails.super.isEnabled();
        }


    }