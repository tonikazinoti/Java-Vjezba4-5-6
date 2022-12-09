package com.programiranjeujavi.vjezba4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String modelName;
    @OneToOne(mappedBy = "device")
    @ToString.Exclude
    private Client client;
    @OneToMany(mappedBy = "device")
    @ToString.Exclude
    private List<DeviceReading> deviceReadings;
}
