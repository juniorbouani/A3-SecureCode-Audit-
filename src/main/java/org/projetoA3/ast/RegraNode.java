package org.projetoA3.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class RegraNode {
    private final String nome;
    private final String nivel;
    private final List<TermoSegNode> condicoes;
    private  final List<String> operadoresLogicos;
    private final String acao;

}
