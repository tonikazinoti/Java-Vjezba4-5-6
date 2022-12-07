package com.programiranjeujavi.vjezba4.repository;

import com.programiranjeujavi.vjezba4.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
