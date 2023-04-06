package com.mescobar.helpdesk.domain.enums;

import java.util.Arrays;

public enum Prioridade {

	BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

	private Integer codigo;
	private String descricao;

	private Prioridade(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Prioridade toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		return Arrays.stream(values()).filter(o -> o.getCodigo().equals(codigo)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Prioridade inválido"));

	}
}
