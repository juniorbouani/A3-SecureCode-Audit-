package org.projetoA3.excessoes;

public class SyntaxException extends RuntimeException {
    public SyntaxException(String message, int line, int column) {
        super("ERRO SINTÁTICO: Linha: " + line + ", Coluna: " + column + ": " + message);
    }
}
