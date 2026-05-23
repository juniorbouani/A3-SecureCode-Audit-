package org.projetoA3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResultadoDTO {
    private final String analiseCompilador;
    private final List<String> violacoes;

}
