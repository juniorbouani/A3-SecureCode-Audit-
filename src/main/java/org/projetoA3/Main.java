package org.projetoA3;

import org.projetoA3.lexer.Scanner;
import org.projetoA3.lexer.Token;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main (String[] args) {
    String code = """
            AUDITORIA MINHA_AUDITORIA DEFINIR
            REGRA SQL_INJECTION NIVEL ALTA SE TOKEN CONTAINS "DROP" ACAO REJEITAR;
            EXECUTAR FIM
            """;

    Scanner scanner = new Scanner(code);
        List<Token> tokens = scanner.tokenize();
        tokens.forEach(System.out::println);
    }
}
