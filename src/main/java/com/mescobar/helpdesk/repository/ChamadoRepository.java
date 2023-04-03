package com.mescobar.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mescobar.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
