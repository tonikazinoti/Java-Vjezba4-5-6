package com.programiranjeujavi.vjezba4.repository;

import com.programiranjeujavi.vjezba4.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
