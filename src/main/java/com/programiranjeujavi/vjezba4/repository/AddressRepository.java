package com.programiranjeujavi.vjezba4.repository;

import com.programiranjeujavi.vjezba4.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByStreetIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCaseAndZipCodeIgnoreCase(
            String street, String city, String country, String zipCode);
}
