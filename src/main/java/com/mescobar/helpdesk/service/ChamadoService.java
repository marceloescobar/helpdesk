package com.mescobar.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mescobar.helpdesk.domain.Chamado;
import com.mescobar.helpdesk.exception.ObjectNotFoundException;
import com.mescobar.helpdesk.repository.ChamadoRepository;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com o ID:" + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}
}
