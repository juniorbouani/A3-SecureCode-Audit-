package org.projetoA3;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.excessoes.LexicalException;
import org.projetoA3.excessoes.SyntaxException;
import org.projetoA3.lexer.Scanner;
import org.projetoA3.lexer.Token;
import org.projetoA3.parser.Parser;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main (String[] args) {
    String code = """
            AUDITORIA MINHA_AUDITORIA DEFINIR
            REGRA SQL_INJECTION  ALTA SE TOKEN CONTAINS "DROP" ACAO REJEITAR;
            EXECUTAR FIM
            """;

    try {
        Scanner scanner = new Scanner(code);
        List<Token> tokens = scanner.tokenize();
        tokens.forEach(System.out::println);

        Parser parser = new Parser(tokens);
        ProjetoNode projeto = parser.parse();
        System.out.println(projeto);

    }
        catch (LexicalException e ) { System.err.println(e.getMessage()); }
        catch (SyntaxException e) { System.err.println(e.getMessage()); }
    }
}
