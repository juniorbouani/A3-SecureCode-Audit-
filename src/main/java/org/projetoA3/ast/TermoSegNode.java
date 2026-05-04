package org.projetoA3.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TermoSegNode {
    private final String chave;
    private final String operador;
    private final String valor;
}
