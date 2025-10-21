package com.jogdev.barbackend.bar.persistence.repository;

import com.jogdev.barbackend.bar.persistence.entity.InventoryLocation;
import com.jogdev.barbackend.util.StatusObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryLocationRepository extends JpaRepository<InventoryLocation, Integer> {

    Optional<InventoryLocation> findInventoryLocationByLocationName(String locationName);
    Page<InventoryLocation> findInventoryLocationByStatusIs(StatusObject status,
                                                            Pageable pageable);
}