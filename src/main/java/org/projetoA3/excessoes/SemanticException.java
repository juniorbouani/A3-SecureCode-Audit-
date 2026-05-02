package org.projetoA3.excessoes;

public class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super("Erro Semântico: " + message);
    }
}
