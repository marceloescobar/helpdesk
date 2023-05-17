package com.mescobar.helpdesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mescobar.helpdesk.domain.Pessoa;
import com.mescobar.helpdesk.repository.PessoaRepository;
import com.mescobar.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PessoaRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Pessoa> pessoaOpt = repository.findByEmail(email);

		Pessoa pessoa = pessoaOpt.orElseThrow(() -> new UsernameNotFoundException(email));

		return new UserSS(pessoa.getId(), pessoa.getEmail(), pessoa.getSenha(), pessoa.getPerfis());

	}

}
