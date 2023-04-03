package com.mescobar.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mescobar.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
