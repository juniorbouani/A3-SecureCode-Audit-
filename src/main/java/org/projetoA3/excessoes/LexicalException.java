package org.projetoA3.excessoes;

public class LexicalException extends RuntimeException {
    public LexicalException(String message, int line, int column) {
        super("ERRO LÉXICO: Linha" + line + ", Coluna: " + column + ": " + message);
    }
}
