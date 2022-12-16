package com.programiranjeujavi.vjezba4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeviceReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_reading_id", nullable = false)
    private Long id;
    private int energyConsumptionKwh;
    @PastOrPresent
    @Builder.Default
    private LocalDate timePeriod = LocalDate.now();
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}
