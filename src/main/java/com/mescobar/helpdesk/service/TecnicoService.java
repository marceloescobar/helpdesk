package com.mescobar.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mescobar.helpdesk.domain.Pessoa;
import com.mescobar.helpdesk.domain.Tecnico;
import com.mescobar.helpdesk.domain.dto.TecnicoDTO;
import com.mescobar.helpdesk.exception.DataIntegritylException;
import com.mescobar.helpdesk.exception.DuplicateCPForEmailException;
import com.mescobar.helpdesk.exception.ObjectNotFoundException;
import com.mescobar.helpdesk.repository.PessoaRepository;
import com.mescobar.helpdesk.repository.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO createTecnicoDTO) {
		createTecnicoDTO.setId(null);
		createTecnicoDTO.setSenha(encoder.encode(createTecnicoDTO.getSenha()));
		validaPorCpfEEmail(createTecnicoDTO);
		Tecnico tecnico = new Tecnico(createTecnicoDTO);
		return repository.save(tecnico);
	}

	private void validaPorCpfEEmail(TecnicoDTO createTecnicoDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(createTecnicoDTO.getCpf());

		if (obj.isPresent() && obj.get().getId() != createTecnicoDTO.getId())
			throw new DuplicateCPForEmailException("CPF já cadastrado no sistema");

		obj = pessoaRepository.findByEmail(createTecnicoDTO.getEmail());

		if (obj.isPresent() && obj.get().getId() != createTecnicoDTO.getId())
			throw new DuplicateCPForEmailException("Email já cadastrado no sistema");

	}

	public Tecnico update(Integer id, TecnicoDTO updateTecnicoDTO) {
		updateTecnicoDTO.setId(id);
		
		Tecnico oldObj = findById(id);
		validaPorCpfEEmail(updateTecnicoDTO);
		
		oldObj = new Tecnico(updateTecnicoDTO);
		
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
	
		if(obj.getChamados().size() > 0) {
			throw new DataIntegritylException("Técnico possui ordens de serviços e não pode ser deletado !");
		}
		
		repository.deleteById(id);
	}
}
