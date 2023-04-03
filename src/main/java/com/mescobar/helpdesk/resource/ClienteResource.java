package com.mescobar.helpdesk.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mescobar.helpdesk.domain.Cliente;
import com.mescobar.helpdesk.domain.dto.ClienteDTO;
import com.mescobar.helpdesk.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findbyId(@PathVariable Integer id) {
		Cliente cliente = service.findById(id);

		return ResponseEntity.ok().body(new ClienteDTO(cliente));
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> clientes = service.findAll();
		List<ClienteDTO> clientesDTO = clientes.stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList());

		return ResponseEntity.ok().body(clientesDTO);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO createClienteDTO) {

		Cliente clienteSaved = service.create(createClienteDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(clienteSaved.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @RequestBody ClienteDTO updateClienteDTO) {
		
		Cliente obj = service.update(id, updateClienteDTO );
		
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
