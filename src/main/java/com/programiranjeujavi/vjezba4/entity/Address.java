package com.programiranjeujavi.vjezba4.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    @OneToOne(mappedBy = "address")
    private Client client;

}
