package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.persistence.entity.security.Role;
import com.jogdev.barbackend.bar.persistence.repository.security.RoleRepository;
import com.jogdev.barbackend.bar.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


/*    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(Role.RoleEnum.SELLER);
    }*/

}

