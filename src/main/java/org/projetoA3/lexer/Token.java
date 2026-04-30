package org.projetoA3.lexer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter //gera um metodo get
@AllArgsConstructor //gera um construtor
@ToString // gera um toString
    public class Token {

    private final TokenType type;
    private final String valor;
    private final int linha;
    private final int coluna;

}
