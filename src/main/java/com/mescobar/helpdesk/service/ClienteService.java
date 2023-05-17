package com.mescobar.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mescobar.helpdesk.domain.Pessoa;
import com.mescobar.helpdesk.domain.Cliente;
import com.mescobar.helpdesk.domain.dto.ClienteDTO;
import com.mescobar.helpdesk.exception.DataIntegritylException;
import com.mescobar.helpdesk.exception.DuplicateCPForEmailException;
import com.mescobar.helpdesk.exception.ObjectNotFoundException;
import com.mescobar.helpdesk.repository.PessoaRepository;
import com.mescobar.helpdesk.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO createClienteDTO) {
		createClienteDTO.setId(null);
		createClienteDTO.setSenha(encoder.encode(createClienteDTO.getSenha()));
		
		validaPorCpfEEmail(createClienteDTO);
		Cliente cliente = new Cliente(createClienteDTO);
		return repository.save(cliente);
	}

	private void validaPorCpfEEmail(ClienteDTO createClienteDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(createClienteDTO.getCpf());

		if (obj.isPresent() && obj.get().getId() != createClienteDTO.getId())
			throw new DuplicateCPForEmailException("CPF já cadastrado no sistema");

		obj = pessoaRepository.findByEmail(createClienteDTO.getEmail());

		if (obj.isPresent() && obj.get().getId() != createClienteDTO.getId())
			throw new DuplicateCPForEmailException("Email já cadastrado no sistema");

	}

	public Cliente update(Integer id, ClienteDTO updateClienteDTO) {
		updateClienteDTO.setId(id);
		
		Cliente oldObj = findById(id);
		validaPorCpfEEmail(updateClienteDTO);
		
		oldObj = new Cliente(updateClienteDTO);
		
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
	
		if(obj.getChamados().size() > 0) {
			throw new DataIntegritylException("Técnico possui ordens de serviços e não pode ser deletado !");
		}
		
		repository.deleteById(id);
	}
}
