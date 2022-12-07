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
    @Column(nullable = false)
    private String modelName;
    @OneToOne(mappedBy = "device")
    @ToString.Exclude
    private Client client;
    @OneToMany(mappedBy = "device")
    @ToString.Exclude
    private List<DeviceReading> deviceReadings;

    public Integer getEnergyConsumptionThisMonthKwh() {
        var min = 0;
        var max = 2000;

        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public List<Integer> getEnergyConsumptionPerMonthInYearKwh() {
        var min = 0;
        var max = 24000;

        var list = new ArrayList<Integer>(12);
        for (var i = 0; i < 12; i++) {
            list.set(i, min + (int)(Math.random() * ((max - min) + 1)));
        }

        return list;
    }
}
