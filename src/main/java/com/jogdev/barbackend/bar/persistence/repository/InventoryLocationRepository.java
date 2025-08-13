package com.jogdev.barbackend.bar.persistence.repository;

import com.jogdev.barbackend.bar.persistence.entity.InventoryLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryLocationRepository extends JpaRepository<InventoryLocation, Integer> {

    Optional<InventoryLocation> findInventoryLocationByLocationName(String locationName);
}