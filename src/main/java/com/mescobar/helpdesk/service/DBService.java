package com.mescobar.helpdesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mescobar.helpdesk.domain.Chamado;
import com.mescobar.helpdesk.domain.Cliente;
import com.mescobar.helpdesk.domain.Tecnico;
import com.mescobar.helpdesk.domain.enums.Perfil;
import com.mescobar.helpdesk.domain.enums.Prioridade;
import com.mescobar.helpdesk.domain.enums.Status;
import com.mescobar.helpdesk.repository.ChamadoRepository;
import com.mescobar.helpdesk.repository.ClienteRepository;
import com.mescobar.helpdesk.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public void instanciaDB() {
		Tecnico tsc1 = new Tecnico(null,"Valdir Cezar","97304728078","valdir@email.com","123");
		tsc1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null,"Linus Torvalds", "72765104255","torvalds@email.com","123");
		
		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "primeiro chamado", tsc1, cli1);
		
		tecnicoRepository.save(tsc1);
		clienteRepository.save(cli1);
		chamadoRepository.save(chamado1);
	}
}
