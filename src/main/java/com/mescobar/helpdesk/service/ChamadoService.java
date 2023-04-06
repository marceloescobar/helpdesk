package com.mescobar.helpdesk.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mescobar.helpdesk.domain.Chamado;
import com.mescobar.helpdesk.domain.Cliente;
import com.mescobar.helpdesk.domain.Tecnico;
import com.mescobar.helpdesk.domain.dto.ChamadoDTO;
import com.mescobar.helpdesk.domain.enums.Prioridade;
import com.mescobar.helpdesk.domain.enums.Status;
import com.mescobar.helpdesk.exception.ObjectNotFoundException;
import com.mescobar.helpdesk.repository.ChamadoRepository;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com o ID:" + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO createChamado) {
		return repository.save(this.criarChamado(createChamado));
	}

	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = this.findById(id);
		oldObj = this.criarChamado(objDTO);
		return repository.save(oldObj);
	}

	private Chamado criarChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());

		Chamado chamado = new Chamado();
		chamado.setId(obj.getId() == null ? null : obj.getId());

		if (Status.ENCERRADO.getCodigo().equals(obj.getStatus())) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());

		return chamado;

	}

}
