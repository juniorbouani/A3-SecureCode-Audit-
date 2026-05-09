package org.projetoA3.excessoes;

public class FileException extends RuntimeException {
    public FileException(String message) {
        super("ERRO DE ARQUIVO: " + message);
    }
}
