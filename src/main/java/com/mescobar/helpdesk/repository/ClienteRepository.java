package com.mescobar.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mescobar.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
