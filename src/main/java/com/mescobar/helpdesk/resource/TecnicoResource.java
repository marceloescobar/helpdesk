package com.mescobar.helpdesk.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mescobar.helpdesk.domain.Tecnico;
import com.mescobar.helpdesk.domain.dto.TecnicoDTO;
import com.mescobar.helpdesk.service.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findbyId(@PathVariable Integer id) {
		Tecnico tecnico = service.findById(id);

		return ResponseEntity.ok().body(new TecnicoDTO(tecnico));
	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> tecnicos = service.findAll();
		List<TecnicoDTO> tecnicosDTO = tecnicos.stream().map(x -> new TecnicoDTO(x)).collect(Collectors.toList());

		return ResponseEntity.ok().body(tecnicosDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO createTecnicoDTO) {

		Tecnico tecnicoSaved = service.create(createTecnicoDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(tecnicoSaved.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @RequestBody TecnicoDTO updateTecnicoDTO) {
		
		Tecnico obj = service.update(id, updateTecnicoDTO );
		
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
