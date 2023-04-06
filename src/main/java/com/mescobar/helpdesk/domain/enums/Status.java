package com.mescobar.helpdesk.domain.enums;

import java.util.Arrays;

public enum Status {

	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

	private Integer codigo;
	private String descricao;

	private Status(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Status toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		return Arrays.stream(values()).filter(o -> o.getCodigo().equals(codigo)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Prioridade inválido"));

	}
}
