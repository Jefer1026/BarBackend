package com.jogdev.barbackend.bar.persistence.entity;

import com.jogdev.barbackend.util.StatusObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity

public class InventoryLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int id;
    private String locationName;

    @Enumerated(EnumType.STRING)
    private StatusObject status;


}
